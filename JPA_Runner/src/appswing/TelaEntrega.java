package appswing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaEntrega {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonApagar;
    private JTextField codigoEntregaTextField, enderecoTextField, entregadorTextField, pedidoTextField;
    private JLabel labelStatus;
    private JComboBox<String> comboEntregadores, comboPedidos;
    
	private JButton btnNewButton;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;

    public TelaEntrega() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Entregas");
        frame.setBounds(100, 100, 800, 487);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                Fachada.inicializar();
                listarEntregas();
                listarEntregadores();
                listarPedidos();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 740, 150);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { 
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String codigoEntrega = (String) table.getValueAt(selectedRow, 0);
                    String endereco = (String) table.getValueAt(selectedRow, 2);
                    String pedidos = (String) table.getValueAt(selectedRow, 3);
                    String entregador = (String) table.getValueAt(selectedRow, 4);

                    codigoEntregaTextField.setText(codigoEntrega);
                    enderecoTextField.setText(endereco);
                    comboEntregadores.setSelectedItem(entregador);
                    comboPedidos.setSelectedItem(pedidos.trim().isEmpty() ? null : pedidos);

                    labelStatus.setText("Entrega selecionada: " + codigoEntrega + ".");
                }
            }
        });

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

        JLabel labelPedido = new JLabel("Pedido:");
        labelPedido.setBounds(557, 228, 150, 20);
        frame.getContentPane().add(labelPedido);
        
        buttonCriar = new JButton("Criar");
        buttonCriar.setBounds(20, 366, 150, 30);
        buttonCriar.addActionListener(this::criarEntrega);
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(180, 366, 150, 30);
        buttonBuscar.addActionListener(this::buscarEntrega);
        frame.getContentPane().add(buttonBuscar);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(340, 366, 150, 30);
        buttonApagar.addActionListener(this::apagarEntrega);
        frame.getContentPane().add(buttonApagar);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.BLUE);
        labelStatus.setBounds(20, 406, 700, 20);
        frame.getContentPane().add(labelStatus);

        comboEntregadores = new JComboBox<>();
        comboEntregadores.setBounds(384, 250, 150, 25);
        frame.getContentPane().add(comboEntregadores);

        comboPedidos = new JComboBox<>();
        comboPedidos.setBounds(556, 250, 150, 25);
        frame.getContentPane().add(comboPedidos);

        
		btnNewButton = new JButton("Pedido");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaPedido();
			}
		});
		btnNewButton.setBounds(21, 10, 112, 14);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_2 = new JButton("Entregador");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntregador();
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
		
		btnNewButton_4 = new JButton("Alterar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaAlterar();
			}
		});
		btnNewButton_4.setBounds(494, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_4);

        listarEntregas();
        listarEntregadores();
        listarPedidos();
        frame.setVisible(true);
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

    private void listarEntregadores() {
        try {
            comboEntregadores.removeAllItems();
            List<Entregador> entregadores = Fachada.listarEntregadores();
            for (Entregador ent : entregadores) {
                comboEntregadores.addItem(ent.getNome());
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar entregadores: " + e.getMessage());
        }
    }

    private void listarPedidos() {
        try {
            comboPedidos.removeAllItems();
            List<Pedido> pedidos = Fachada.listarPedidos();
            for (Pedido ped : pedidos) {
                comboPedidos.addItem(ped.getDescricao());
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar pedidos: " + e.getMessage());
        }
    }


    private void criarEntrega(ActionEvent e) {
        try {
            String codigo = codigoEntregaTextField.getText().trim();
            if (codigo.isEmpty()) {
                labelStatus.setText("Código não pode estar vazio");
                return;
            }
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

    private void buscarEntrega(ActionEvent e) {
        new TelaConsulta();
    }

    private void apagarEntrega(ActionEvent e) {
        try {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String codigoEntrega = (String) table.getValueAt(selectedRow, 0);

                Fachada.excluirEntrega(codigoEntrega);

                labelStatus.setText("Entrega apagada com sucesso!");
                listarEntregas();
            } else {
                labelStatus.setText("Por favor, selecione uma entrega para apagar.");
            }
        } catch (Exception ex) {
            labelStatus.setText("Erro ao apagar entrega: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

}
