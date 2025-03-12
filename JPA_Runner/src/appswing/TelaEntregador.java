package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import modelo.Entregador;
import regras_negocio.Fachada;

public class TelaEntregador {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton buttonCreate, buttonUpdate, buttonDelete, buttonClear;
	private JLabel label, label2, label3, label4;
	private JPanel panel;
	private JTextField textFieldName;
	private Timer timer;

	public TelaEntregador() {
		initialize();
	}

	private void initialize() {
		frame = new JDialog();
		frame.setResizable(true);
		frame.setModal(true);
		frame.setTitle("Entregador");
		frame.setBounds(100, 100, 813, 438);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Fachada.inicializar();
				timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String titulo = "Entregador - " + java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
						frame.setTitle(titulo);
						listagem();
					}
				});
				timer.setRepeats(true);
				timer.setDelay(3000);
				timer.start();
			}
/*
			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
				Fachada.finalizar();
			}*/
		});

		// Scroll Pane
		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 39, 751, 179);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exibirEntregadorSelecionada();
			}
		});

		table.setGridColor(Color.BLACK);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 374, 735, 14);
		frame.getContentPane().add(label);

		label2 = new JLabel("Selecione um entregador para editar");
		label2.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(label2);

		label3 = new JLabel("Nome:");
		label3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setBounds(21, 239, 62, 14);
		frame.getContentPane().add(label3);

		label4 = new JLabel("Entregas:");
		label4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label4.setHorizontalAlignment(SwingConstants.RIGHT);
		label4.setBounds(21, 264, 62, 14);
		frame.getContentPane().add(label4);

		// Text Fields
		textFieldName = new JTextField();
		textFieldName.setFont(new Font("Dialog", Font.PLAIN, 12));
		textFieldName.setColumns(10);
		textFieldName.setBackground(Color.WHITE);
		textFieldName.setBounds(93, 236, 165, 20);
		frame.getContentPane().add(textFieldName);

		// Buttons
		buttonCreate = new JButton("Criar");
		buttonCreate.setToolTipText("Cadastrar novo entregador");
		buttonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldName.getText().isEmpty())
					label.setText("Nome vazio");
				else
					criarEntregador();
			}
		});
		buttonCreate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		buttonCreate.setBounds(93, 317, 62, 23);
		frame.getContentPane().add(buttonCreate);

		buttonUpdate = new JButton("Atualizar");
		buttonUpdate.setToolTipText("Atualizar entregador");
		buttonUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldName.getText().isEmpty())
					label.setText("Nome vazio");
				else
					atualizarEntregadorSelecionada();
			}
		});
		buttonUpdate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		buttonUpdate.setBounds(170, 317, 87, 23);
		frame.getContentPane().add(buttonUpdate);

		buttonDelete = new JButton("Apagar");
		buttonDelete.setToolTipText("Apagar entregador e seus dados");
		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldName.getText().isEmpty())
					label.setText("Nome vazio");
				else
					apagarEntregadorSelecionada();
			}
		});
		buttonDelete.setFont(new Font("Tahoma", Font.PLAIN, 11));
		buttonDelete.setBounds(267, 317, 74, 23);
		frame.getContentPane().add(buttonDelete);

		buttonClear = new JButton("Limpar");
		buttonClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldName.setText("");
			}
		});
		buttonClear.setBounds(274, 235, 89, 23);
		frame.getContentPane().add(buttonClear);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);
			model.addColumn("Id");
			model.addColumn("Nome");
			model.addColumn("Entregas");

			String entregas;
			java.util.List<Entregador> lista = Fachada.listarEntregadores();

			for (Entregador x : lista) {
				Entregador e = Fachada.localizarEntregador(x.getNome());
				entregas = String.valueOf(e.getEntregas().size()); // Contabilizando as entregas
				model.addRow(new Object[]{e.getId(), e.getNome(), entregas});
			}

			label2.setText("Resultados: " + lista.size() + " entregadores - selecione uma linha para editar");
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

	public void exibirEntregadorSelecionada() {
		try {
			label.setText("");
			if (table.getSelectedRow() >= 0) {
				String nome = (String) table.getValueAt(table.getSelectedRow(), 1);
				Entregador e = Fachada.localizarEntregador(nome);
				textFieldName.setText(nome);
			}
		} catch (Exception e) {
			label.setText(e.getMessage());
		}
	}

	public void criarEntregador() {
		try {
			Fachada.cadastrarEntregador(textFieldName.getText());
			label.setText("Entregador criado com sucesso!");
			listagem();
		} catch (Exception e) {
			label.setText(e.getMessage());
		}
	}

	public void atualizarEntregadorSelecionada() {
		try {
			Fachada.atualizarEntregador(textFieldName.getText());
			label.setText("Entregador atualizado com sucesso!");
			listagem();
		} catch (Exception e) {
			label.setText(e.getMessage());
		}
	}

	public void apagarEntregadorSelecionada() {
		try {
			Fachada.apagarEntregador(textFieldName.getText());
			label.setText("Entregador apagado com sucesso!");
			listagem();
		} catch (Exception e) {
			label.setText(e.getMessage());
		}
	}
}
