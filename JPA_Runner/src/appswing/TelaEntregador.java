/**********************************
 * IFPB - SI
 * POB - Persistencia de Objetos
 * Prof. Fausto Ayres
 **********************************/
package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import modelo.Entregador;
import modelo.Telefone;
import regras_negocio.Fachada;

public class TelaEntregador {
	private JDialog frame;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton button_3;
	private JLabel label;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JButton button_1;
	private JButton button_2;
	private JButton button_4;
	private JLabel label_8;
	private JPanel panel;
	private JLabel label_1;
	private JButton button_5;
	private JButton button_6;
	private BufferedImage buffer; // armazena a foto na mem�ria durante a edicao

	private Timer timer;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_4;
	private JTextField textField_3;
	private JLabel label_5;
	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// TelaReuniao window = new TelaReuniao();
	// window.frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public TelaEntregador() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JDialog();
		frame.setResizable(false);
		frame.setModal(true);
		frame.setTitle("Entregadors");
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
						String titulo = "Entregadors - "
								+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));
						frame.setTitle(titulo);
						listagem();
					}
				});
				timer.setRepeats(true);
				timer.setDelay(3000);
				timer.start();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
				Fachada.finalizar();
			}
		});

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 39, 751, 179);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};

		// ****************************************
		// evento de click numa linha da tabela
		// ****************************************
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				exibirEntregadorSelecionada();
			}
		});

		table.setGridColor(Color.BLACK);
		table.setRequestFocusEnabled(false);
		table.setFocusable(false);
		table.setBackground(Color.WHITE);
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(true);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(table);
		table.setBorder(new LineBorder(new Color(0, 0, 0)));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.setShowGrid(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		label = new JLabel("");
		label.setForeground(Color.RED);
		label.setBounds(21, 374, 735, 14);
		frame.getContentPane().add(label);

		label_2 = new JLabel("selecione uma entregador para editar");
		label_2.setBounds(21, 216, 394, 14);
		frame.getContentPane().add(label_2);

		label_3 = new JLabel("nome:");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_3.setBounds(21, 239, 62, 14);
		frame.getContentPane().add(label_3);

		label_4 = new JLabel("nascimento:");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_4.setBounds(10, 264, 73, 14);
		frame.getContentPane().add(label_4);

		label_8 = new JLabel("novo numero:");
		label_8.setHorizontalAlignment(SwingConstants.LEFT);
		label_8.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_8.setBounds(21, 314, 74, 14);
		frame.getContentPane().add(label_8);

		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Foto",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(402, 225, 102, 105);
		frame.getContentPane().add(panel);

		label_1 = new JLabel("sem foto");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(10, 21, 78, 73);
		panel.add(label_1);

		button_1 = new JButton("Criar");
		button_1.setToolTipText("cadastrar nova entregador");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().isEmpty())
					label.setText("nome vazio");
				else
					criarEntregador();
			}
		});
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(93, 339, 62, 23);
		frame.getContentPane().add(button_1);

		button_2 = new JButton("Atualizar");
		button_2.setToolTipText("atualizar entregador ");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().isEmpty())
					label.setText("nome vazio");
				else
					atualizarEntregadorSelecionada();
			}
		});
		button_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_2.setBounds(170, 339, 87, 23);
		frame.getContentPane().add(button_2);

		button_3 = new JButton("Apagar");
		button_3.setToolTipText("apagar entregador e seus dados");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_1.getText().isEmpty())
					label.setText("nome vazio");
				else
					apagarEntregadorSelecionada();
			}
		});
		button_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_3.setBounds(267, 339, 74, 23);
		frame.getContentPane().add(button_3);

		button_4 = new JButton("Limpar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_2.setText("");
				textField_4.setText("");
			}
		});
		button_4.setBounds(274, 235, 89, 23);
		frame.getContentPane().add(button_4);

		button_5 = new JButton("Selecionar");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textField_2.getText().isEmpty()) {
					label.setText("selecione uma entregador");
					return;
				}
				File file = selecionarArquivoFoto();
				if (file == null)
					return; // arquivo nao foi selecionado

				try {
					buffer = ImageIO.read(file); // ler imagem do arquivo
					ImageIcon icon = new ImageIcon(
							buffer.getScaledInstance(buffer.getWidth(), buffer.getHeight(), Image.SCALE_DEFAULT));
					icon.setImage(icon.getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), 1));
					label_1.setIcon(icon);
				} catch (IOException ex) {
					label.setText(ex.getMessage());
				}
			}
		});
		button_5.setBounds(518, 229, 100, 23);
		frame.getContentPane().add(button_5);

		button_6 = new JButton("Limpar");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buffer = null;
				label_1.setIcon(null);
				label_1.setText("sem foto");
				label.setText("");
			}
		});
		button_6.setBounds(518, 264, 100, 23);
		frame.getContentPane().add(button_6);

		textField_1 = new JTextField();
		textField_1.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(93, 236, 165, 20);
		frame.getContentPane().add(textField_1);

		textField_2 = new JTextField();
		textField_2.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_2.setColumns(10);
		textField_2.setBounds(93, 261, 86, 20);
		frame.getContentPane().add(textField_2);

		textField_4 = new JTextField();
		textField_4.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_4.setColumns(10);
		textField_4.setBounds(93, 285, 264, 20);
		frame.getContentPane().add(textField_4);

		textField_3 = new JTextField();
		textField_3.setFont(new Font("Dialog", Font.PLAIN, 12));
		textField_3.setColumns(10);
		textField_3.setBounds(93, 310, 86, 20);
		frame.getContentPane().add(textField_3);

		label_5 = new JLabel("apelidos:");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(21, 289, 62, 14);
		frame.getContentPane().add(label_5);

		frame.setVisible(true);
	}

	public void listagem() {
		try {
			// objeto model contem todas as linhas e colunas da tabela
			DefaultTableModel model = new DefaultTableModel();
			table.setModel(model);

			// criar as colunas (0,1,2) da tabela
			model.addColumn("Id");
			model.addColumn("Nome");
			model.addColumn("Nascimento");
			model.addColumn("Apelidos");
			model.addColumn("Telefones");
			
			// criar as linhas da tabela
			String telefones, apelidos;
			List<Entregador> lista = Fachada.listarEntregadores();
			
			for (Entregador x : lista) {
				Entregador e = Fachada.localizarEntregador(x.getNome());
				
				apelidos = String.join(",", e.getApelidos()); // concatena strings
				if (e.getTelefones().size() == 0)
					telefones = "sem telefone";
				else {
					telefones = "";
					for (Telefone t : e.getTelefones())
						telefones = telefones + " " + t.getNumero();
				}
				model.addRow(new Object[] { e.getId(), e.getNome(), e.getDtNascimento(), apelidos,telefones });
			}

			label_2.setText("resultados: " + lista.size() + " entregadores   - selecione uma linha para editar");
			// redimensionar colunas
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // desabilita
			table.getColumnModel().getColumn(0).setMaxWidth(40); // coluna id
			table.getColumnModel().getColumn(3).setMinWidth(200); // coluna dos apelidos
			table.getColumnModel().getColumn(4).setMinWidth(200); // coluna dos telefones
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // desabilita

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

	public void exibirEntregadorSelecionada() {
		try {
			label.setText("");
			if (table.getSelectedRow() >= 0) {
				// ler do banco a entregador selecionada no grid pelo nome
				String nome = (String) table.getValueAt(table.getSelectedRow(), 1);
				Entregador e = Fachada.localizarEntregador(nome);
				textField_1.setText(nome);
				textField_2.setText(e.getDtNascimento());
				textField_4.setText(String.join(",", e.getApelidos()));
				textField_3.setText("");
			}
		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

	public void apagarEntregadorSelecionada() {
		try {
			label.setText("");
			textField_3.setText("");
			String nome = textField_1.getText();
			
			Object[] options = { "Confirmar", "Cancelar" };
			int escolha = JOptionPane.showOptionDialog(null,
					"Esta opera��o apagar� os telefones e remover� as reunioes de " + nome, "Alerta",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			if (escolha == 0) {
				Fachada.apagarEntregador(nome);
				label.setText("entregador excluida");
				listagem(); // listagem
			} else
				label.setText("exclus�o cancelada");

		} catch (Exception erro) {
			label.setText(erro.getMessage());
		}
	}

	public void criarEntregador() {
		try {
			label.setText("");
			String nome = textField_1.getText().trim();
			String nascimento = textField_2.getText().trim();
			String[] apelidos = textField_4.getText().trim().split(",");

			Fachada.criarEntregador(nome, nascimento, new ArrayList<>(Arrays.asList(apelidos)));
			String numero = textField_3.getText();
			if (!numero.isEmpty())
				Fachada.criarTelefone(nome, numero);
			label.setText("entregador criada");
			listagem();

		} catch (Exception ex) {
			label.setText(ex.getMessage());
		}
	}

	public void atualizarEntregadorSelecionada() {
		try {
			label.setText("");
			String nome = textField_1.getText();
			String[] apelidos = textField_4.getText().trim().split(",");
			String nascimento = textField_2.getText();
			Fachada.alterarEntregador(nome, nascimento, new ArrayList<>(Arrays.asList(apelidos)));
			byte[] bytesfoto = null;
			if (buffer != null)	
				try {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ImageIO.write(buffer, "jpg", baos);
					bytesfoto = baos.toByteArray();
					baos.close();
				} catch (IOException ex1) {
					label.setText("problema na convers�o da imagem em bytes");
				}
			
			String numero = textField_3.getText();
			if (!numero.isEmpty())
				Fachada.criarTelefone(nome, numero);

			label.setText("entregador atualizada");
			listagem();
		} catch (Exception ex2) {
			label.setText(ex2.getMessage());
		}
	}

}
