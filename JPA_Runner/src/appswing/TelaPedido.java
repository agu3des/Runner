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

import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaPedido {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonBuscarValor, buttonApagar;
    private JTextField textFieldCodigoPedido, textFieldValor, textFieldDescricao;
    private JLabel labelStatus;
    private JLabel labelEscolhaOpcao;
    private JLabel labelCodigoPedido, labelValor, labelDescricao;
    
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;

    public TelaPedido() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Pedidos");
        frame.setBounds(100, 100, 744, 428);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                Fachada.inicializar();
                listarPedidos();
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
        labelEscolhaOpcao.setFont(new Font("Tahoma", Font.PLAIN, 14));
        frame.getContentPane().add(labelEscolhaOpcao);

        labelCodigoPedido = new JLabel("Código do Pedido:");
        labelCodigoPedido.setBounds(21, 230, 150, 20);
        frame.getContentPane().add(labelCodigoPedido);

        textFieldCodigoPedido = new JTextField();
        textFieldCodigoPedido.setBounds(21, 250, 150, 30);
        frame.getContentPane().add(textFieldCodigoPedido);

        labelValor = new JLabel("Valor:");
        labelValor.setBounds(181, 230, 150, 20);
        frame.getContentPane().add(labelValor);

        textFieldValor = new JTextField();
        textFieldValor.setBounds(181, 250, 150, 30);
        frame.getContentPane().add(textFieldValor);

        labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(341, 230, 150, 20);
        frame.getContentPane().add(labelDescricao);

        textFieldDescricao = new JTextField();
        textFieldDescricao.setBounds(341, 250, 310, 30);
        frame.getContentPane().add(textFieldDescricao);

        buttonCriar = new JButton("Criar");
        buttonCriar.setBounds(21, 340, 150, 30);
        buttonCriar.addActionListener(e -> criarPedido());
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 340, 150, 30);
        buttonBuscar.addActionListener(e -> buscarPedido());
        frame.getContentPane().add(buttonBuscar);

        buttonBuscarValor = new JButton("Buscar por valor");
        buttonBuscarValor.setBounds(341, 340, 150, 30);
        buttonBuscarValor.addActionListener(e -> mostrarPedidosPorValor());
        frame.getContentPane().add(buttonBuscarValor);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(501, 340, 150, 30);
        buttonApagar.addActionListener(e -> apagarPedido());
        frame.getContentPane().add(buttonApagar);

		
		btnNewButton_1 = new JButton("Entregador");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntregador();
			}
		});
		btnNewButton_1.setBounds(180, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Entrega");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntrega();
			}
		});
		btnNewButton_2.setBounds(21, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Consultas");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaConsulta();
			}
		});
		btnNewButton_3.setBounds(341, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_3);
		
		btnNewButton_4 = new JButton("Alterar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaAlterar();
			}
		});
		btnNewButton_4.setBounds(492, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_4);
        
        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.BLUE);
        labelStatus.setBounds(21, 372, 677, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    private void criarPedido() {
        try {
            String codigo = textFieldCodigoPedido.getText().trim();
            if (codigo.isEmpty()) {
                labelStatus.setText("Código não pode estar vazio");
                return;
            }
            double valor = Double.parseDouble(textFieldValor.getText().trim());
            String descricao = textFieldDescricao.getText().trim();
            Fachada.criarPedido(codigo, LocalDate.now(), valor, descricao);
            labelStatus.setText("Pedido criado com sucesso!");
            listarPedidos();
        } catch (Exception e) {
            labelStatus.setText("Erro ao criar pedido: " + e.getMessage());
        }
    }

    public void listarPedidos() {
        try {
            List<Pedido> lista = Fachada.listarPedidos();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("Código do Pedido");
            model.addColumn("Data do Pedido");
            model.addColumn("Valor");
            model.addColumn("Descrição");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Pedido p : lista) {
                model.addRow(new Object[]{p.getCodigoPedido(), p.getDataPedido().format(formatter), p.getValor(), p.getDescricao()});
            }
            labelStatus.setText("Resultados: " + lista.size() + " pedidos - selecione uma linha para editar");
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    private void buscarPedido() {
    	new TelaConsulta(); 
    }

    private void mostrarPedidosPorValor() {
        try {
            double valor = Double.parseDouble(textFieldValor.getText().trim());
            List<Pedido> listaPedidos = Fachada.consultarPedidoPorValor(valor);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Pedido p : listaPedidos) {
                model.addRow(new Object[]{p.getCodigoPedido(), p.getDataPedido().format(formatter), p.getValor(), p.getDescricao()});
            }
            labelStatus.setText("Pedidos encontrados!");
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar pedidos por valor: " + e.getMessage());
        }
    }

    private void apagarPedido() {
        try {
            Fachada.excluirPedido(textFieldCodigoPedido.getText().trim());
            labelStatus.setText("Pedido apagado com sucesso!");
            listarPedidos();
        } catch (Exception e) {
            labelStatus.setText("Erro ao apagar pedido: " + e.getMessage());
        }
    }
}
