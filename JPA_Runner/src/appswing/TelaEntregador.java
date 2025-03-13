package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import modelo.Entregador;
import regras_negocio.Fachada;

public class TelaEntregador {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonAddEntrega, buttonUpdate, buttonApagar, buttonCreate, buttonBuscar;
    private JLabel label, label2, label3, label4, label5;
    private JTextField textFieldName, textFieldEntrega;
    
	private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;


    public TelaEntregador() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(true);
        frame.setModal(true);
        frame.setTitle("Entregador");
        frame.setBounds(100, 100, 813, 437);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                Fachada.inicializar();
                listarEntregadores();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 33, 751, 179);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);


        table.setGridColor(Color.BLACK);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);

        label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setBounds(21, 363, 735, 14);
        frame.getContentPane().add(label);

        label2 = new JLabel("Selecione um entregador para editar");
        label2.setBounds(21, 216, 394, 14);
        frame.getContentPane().add(label2);

        label3 = new JLabel("Nome:");
        label3.setVerticalAlignment(SwingConstants.BOTTOM);
        label3.setFont(new Font("Arial", Font.PLAIN, 11));
        label3.setBounds(21, 239, 62, 14);
        frame.getContentPane().add(label3);

        label4 = new JLabel("Nova Entrega:");
        label4.setFont(new Font("Arial", Font.PLAIN, 11));
        label4.setBounds(21, 264, 80, 14);
        frame.getContentPane().add(label4);
        
        label5 = new JLabel("Status:");
        label5.setFont(new Font("Arial", Font.PLAIN, 11));
        label5.setBounds(21, 264, 80, 14);
        frame.getContentPane().add(label4);
        
        textFieldName = new JTextField();
        textFieldName.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldName.setBounds(93, 236, 165, 20);
        frame.getContentPane().add(textFieldName);

        textFieldEntrega = new JTextField();
        textFieldEntrega.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldEntrega.setBounds(93, 262, 165, 20);
        frame.getContentPane().add(textFieldEntrega);

        buttonCreate = new JButton("Criar Entregador");
        buttonCreate.setBounds(53, 319, 150, 30);
        buttonCreate.addActionListener(e -> criarEntregador());
        frame.getContentPane().add(buttonCreate);

        buttonAddEntrega = new JButton("Adicionar Entrega");
        buttonAddEntrega.setBounds(606, 319, 150, 30);
        buttonAddEntrega.addActionListener(e -> associarEntregaAEntregador());
        frame.getContentPane().add(buttonAddEntrega);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(237, 319, 150, 30);
        buttonBuscar.addActionListener(this::buscarEntregador);
        frame.getContentPane().add(buttonBuscar);
        
        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(424, 319, 150, 30);
        buttonApagar.addActionListener(this::apagarEntregador);
        frame.getContentPane().add(buttonApagar);
        
        
        
		btnNewButton = new JButton("Pedido");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaPedido();
			}
		});
		btnNewButton.setBounds(21, 10, 112, 14);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_2 = new JButton("Entrega");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntrega();
			}
		});
		btnNewButton_2.setBounds(175, 9, 112, 14);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Consultas");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaConsulta();
			}
		});
		btnNewButton_3.setBounds(342, 9, 112, 14);
		frame.getContentPane().add(btnNewButton_3);
        
        
        frame.setVisible(true);
    }

    private void criarEntregador() {
        try {
            String nome = textFieldName.getText().trim();
            if (nome.isEmpty()) {
                label.setText("Nome não pode estar vazio");
                return;
            }
            Fachada.criarEntregador(nome);
            label.setText("Entregador criado com sucesso!");
            listarEntregadores();
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }

    private void listarEntregadores() {
        try {      
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID do Entregador");
            model.addColumn("Nome");
            model.addColumn("Entregas");

            String entregas;
            List<Entregador> lista = Fachada.listarEntregadores();

            for (Entregador e : lista) {
                Entregador entregador = Fachada.localizarEntregador(e.getNome());

                if (entregador.getEntregas().size() == 0) {
                    entregas = "sem entrega";
                } else {
                    entregas = "";
                    for (Entrega entrega : entregador.getEntregas()) {
                        entregas += " " + entrega.getCodigoEntrega();
                    }
                }

                model.addRow(new Object[]{entregador.getId(), entregador.getNome(), entregas});
            }
      
			label5.setText("resultados: " + lista.size() + " entregadores - selecione uma linha para editar");

            // redimensionar colunas
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 

        } catch (Exception erro) {
            label.setText("Erro ao listar entregadores: " + erro.getMessage());
        }
    }


    private void buscarEntregador(ActionEvent e) {
    	new TelaConsulta();
    }

    private void apagarEntregador(ActionEvent e) {
        try {
            Fachada.excluirEntrega(textFieldName.getText().trim());
            label5.setText("Entrega apagada com sucesso!");
            listarEntregadores();
        } catch (Exception ex) {
        	label5.setText("Erro ao apagar entrega: " + ex.getMessage());
        }
    }

    
    private void associarEntregaAEntregador() {
        try {
            String nome = textFieldName.getText().trim();
            String entregaCodigo = textFieldEntrega.getText().trim();
            if (nome.isEmpty() || entregaCodigo.isEmpty()) {
                label.setText("Nome ou entrega vazios");
                return;
            }

            Entregador entregador = Fachada.localizarEntregador(nome);
            Entrega entrega = Fachada.localizarEntrega(entregaCodigo);

            if (entregador != null && entrega != null) {
                Fachada.alterarEntregadorDeEntrega(entregaCodigo, nome);  
                label.setText("Entrega associada com sucesso!");
                listarEntregadores();
            } else {
                label.setText("Entregador ou entrega inválidos!");
            }
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }
}
