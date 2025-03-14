package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaEntrega {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonApagar;
    private JTextField codigoEntregaTextField, enderecoTextField, entregadorTextField, pedidoTextField;
    private JLabel labelStatus;
    private JLabel labelEscolhaOpcao;
    
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_3;

    public TelaEntrega() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Entregas");
        frame.setBounds(100, 100, 744, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                Fachada.inicializar();
                listarEntregas();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 63, 685, 155);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        labelEscolhaOpcao = new JLabel("Escolha uma opção");
        labelEscolhaOpcao.setBounds(21, 27, 200, 20);
        labelEscolhaOpcao.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.getContentPane().add(labelEscolhaOpcao);

        buttonCriar = new JButton("Criar");
        buttonCriar.setBounds(21, 300, 150, 30);
        buttonCriar.addActionListener(this::criarEntrega);
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 300, 150, 30);
        buttonBuscar.addActionListener(this::buscarEntrega);
        frame.getContentPane().add(buttonBuscar);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(341, 300, 150, 30);
        buttonApagar.addActionListener(this::apagarEntrega);
        frame.getContentPane().add(buttonApagar);
        
        
		btnNewButton = new JButton("Pedido");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaPedido();
			}
		});
		btnNewButton.setBounds(21, 10, 112, 14);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Entregador");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntregador();
			}
		});
		btnNewButton_1.setBounds(181, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_1);

		
		btnNewButton_3 = new JButton("Consultas");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaConsulta();
			}
		});
		btnNewButton_3.setBounds(339, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_3);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.BLUE);
        labelStatus.setBounds(21, 400, 677, 14);
        frame.getContentPane().add(labelStatus);

        JLabel labelCodigoEntrega = new JLabel("Código da Entrega:");
        labelCodigoEntrega.setBounds(21, 230, 150, 20);
        frame.getContentPane().add(labelCodigoEntrega);

        codigoEntregaTextField = new JTextField();
        codigoEntregaTextField.setBounds(21, 250, 150, 25);
        frame.getContentPane().add(codigoEntregaTextField);

        JLabel labelEndereco = new JLabel("Endereço:");
        labelEndereco.setBounds(200, 228, 150, 20);
        frame.getContentPane().add(labelEndereco);

        enderecoTextField = new JTextField();
        enderecoTextField.setBounds(200, 250, 150, 25);
        frame.getContentPane().add(enderecoTextField);

        JLabel labelEntregador = new JLabel("Entregador:");
        labelEntregador.setBounds(384, 228, 150, 20);
        frame.getContentPane().add(labelEntregador);

        entregadorTextField = new JTextField();
        entregadorTextField.setBounds(384, 250, 150, 25);
        frame.getContentPane().add(entregadorTextField);

        JLabel labelPedido = new JLabel("Pedido:");
        labelPedido.setBounds(557, 228, 150, 20);
        frame.getContentPane().add(labelPedido);

        pedidoTextField = new JTextField();
        pedidoTextField.setBounds(556, 250, 150, 25);
        frame.getContentPane().add(pedidoTextField);

        listarEntregas();
        System.out.println(labelStatus);
        frame.setVisible(true);
    }

    private void criarEntrega(ActionEvent e) {
        try {
            String codigo = codigoEntregaTextField.getText().trim();
            String endereco = enderecoTextField.getText().trim();
            String entregador = entregadorTextField.getText().trim(); 
            String pedido = pedidoTextField.getText().trim();         
            Fachada.criarEntrega(codigo, LocalDate.now(), endereco, entregador, pedido);
            labelStatus.setText("Entrega criada com sucesso!");
            listarEntregas();
        } catch (Exception ex) {
            labelStatus.setText("Erro ao criar entrega: " + ex.getMessage());
        }
    }

    private void listarEntregas() {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("Código");
            model.addColumn("Data");
            model.addColumn("Endereço");
            model.addColumn("Pedidos");
            model.addColumn("Entregador");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String pedidos, entregador;
            List<Entrega> lista = Fachada.listarEntregas();

            for (Entrega e : lista) {
            	Entrega entrega = Fachada.localizarEntrega(e.getCodigoEntrega());

                if (entrega.getPedidos().size() == 0) {
                    pedidos = "sem pedido";
                } else {
                    pedidos = "";
                    for (Pedido p : entrega.getPedidos()) {
                        pedidos += " " + p.getCodigoPedido();
                    }
                }            	
                
            	if (entrega.getEntregador() != null) {
            	    entregador = entrega.getEntregador().getNome();
            	} else {
            		entregador = "Desconhecido";
            	}
            	
            	model.addRow(new Object[]{e.getCodigoEntrega(), e.getDataEntrega().format(formatter), e.getEndereco(), pedidos, entregador});
            }

            labelStatus.setText("Resultados: " + lista.size() + " entregas - selecione uma linha para editar");

            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); 

        } catch (Exception erro) {
            labelStatus.setText("Erro ao listar entregas: " + erro.getMessage());
            System.out.println(erro.getMessage());
        }
    }

    private void buscarEntrega(ActionEvent e) {
    	new TelaConsulta();
    }

    private void apagarEntrega(ActionEvent e) {
        try {
            Fachada.excluirEntrega(codigoEntregaTextField.getText().trim());
            labelStatus.setText("Entrega apagada com sucesso!");
            listarEntregas();
        } catch (Exception ex) {
            labelStatus.setText("Erro ao apagar entrega: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaEntrega();
    }
}
