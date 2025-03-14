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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaConsulta {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton button;
    private JLabel label;
    private JLabel label_4;
    private JComboBox<String> comboBox;
    private JTextField textField;
    
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_4;
	
	private Timer timerReset;


    public TelaConsulta() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Consultas");
        frame.setBounds(100, 100, 800, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 129, 750, 200);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setGridColor(Color.BLACK);
        table.setFocusable(false);
        table.setBackground(Color.LIGHT_GRAY);
        table.setFillsViewportHeight(true);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        scrollPane.setViewportView(table);
        table.setBorder(new LineBorder(new Color(0, 0, 0)));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        label = new JLabel("");
        label.setForeground(Color.BLUE);
        label.setBounds(20, 333, 750, 20);
        frame.getContentPane().add(label);

        label_4 = new JLabel("Resultados:");
        label_4.setBounds(20, 280, 750, 20);
        frame.getContentPane().add(label_4);

        button = new JButton("Consultar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBounds(630, 75, 120, 30);
        frame.getContentPane().add(button);
        
        
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
		btnNewButton_1.setBounds(334, 9, 112, 14);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Entrega");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntrega();
			}
		});
		btnNewButton_2.setBounds(175, 9, 112, 14);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_4 = new JButton("Alterar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaAlterar();
			}
		});
		btnNewButton_4.setBounds(485, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_4);

        comboBox = new JComboBox<>(new String[] {
                "Consultar pedidos pelo código",
                "Consultar entregadores pelo nome",
                "Consultar entregas pelo código",
                "Consultar entregas pela data 'yyyy-MM-dd'",
                "Consultar entregadores com n entregas",
                "Consultar pedidos pelo valor"
        });
        comboBox.setBounds(20, 36, 600, 30);
        frame.getContentPane().add(comboBox);

        textField = new JTextField();
        textField.setBounds(20, 76, 600, 30);
        frame.getContentPane().add(textField);

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                String input = textField.getText().trim();

                if (input.isEmpty()) {
                    label.setText("Por favor, insira um valor para a consulta.");
                    return;
                }

                try {
                    switch (index) {
                        case 0:
                            listarPedidos(Fachada.localizarPedido(input));
                            break;
                        case 1:
                            listarEntregadores(Fachada.localizarEntregador(input));
                            break;
                        case 2:
                            listarEntregas(Fachada.localizarEntrega(input));
                            break;
                        case 3:
                        	LocalDate data = LocalDate.parse(input.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            listarEntregas(Fachada.consultarEntregaPorData(data));
                            break;
                        case 4:
                            listarEntregadores(Fachada.consultarPorNEntregas(Integer.parseInt(input)));
                            break;
                        case 5:
                            listarPedidos(Fachada.consultarPedidoPorValor(Double.parseDouble(input)));
                            break;
                    }
                    
                 
                    if (timerReset != null && timerReset.isRunning()) {
                        timerReset.stop();
                    }

                    timerReset = new Timer(60000, event -> resetarConsulta());
                    timerReset.setRepeats(false); 
                    timerReset.start(); 
                    
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
    }

    private void listarPedidos(Object resultado) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Código");
        model.addColumn("Data");
        model.addColumn("Valor");
        model.addColumn("Descrição");
        
        int quantidadePedidos = 0;

        if (resultado instanceof List) {
            List<Pedido> lista = (List<Pedido>) resultado;
            quantidadePedidos = lista.size();
            for (Pedido p : lista) {
                model.addRow(new Object[] { p.getCodigoPedido(), p.getDataPedido(), p.getValor(), p.getDescricao() });
            }
        } else if (resultado instanceof Pedido) {
            Pedido p = (Pedido) resultado;
            quantidadePedidos = 1;
            model.addRow(new Object[] { p.getCodigoPedido(), p.getDataPedido(), p.getValor(), p.getDescricao() });
        }
        label.setText("Resultados: " + quantidadePedidos + " pedido(s)");
    }

    private void listarEntregadores(Object resultado) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Nome");
        model.addColumn("Número de Entregas");
        
        int quantidadeEntregadores = 0;

        if (resultado instanceof List) {
            List<Entregador> lista = (List<Entregador>) resultado;
            quantidadeEntregadores = lista.size();
            for (Entregador e : lista) {
                model.addRow(new Object[] { e.getNome(), e.getEntregas().size() });
            }
        } else if (resultado instanceof Entregador) {
            Entregador e = (Entregador) resultado;
            quantidadeEntregadores = 1;
            model.addRow(new Object[] { e.getNome(), e.getEntregas().size() });
        }
        label.setText("Resultados: " + quantidadeEntregadores + " entregador(es)");
    }

    private void listarEntregas(Object resultado) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Código");
        model.addColumn("Data");
        model.addColumn("Endereço");
        model.addColumn("Entregador");
        model.addColumn("Id do Pedido");
        
        int quantidadeEntregas = 0;

        if (resultado instanceof List) {
            List<Entrega> lista = (List<Entrega>) resultado;
            quantidadeEntregas = lista.size();
            for (Entrega e : lista) {
                model.addRow(new Object[] {
                        e.getCodigoEntrega(),
                        e.getDataEntrega(),
                        e.getEndereco(),
                        e.getEntregador() != null ? e.getEntregador().getNome() : "Sem entregador",
                        !e.getPedidos().isEmpty() ? e.getPedidos().get(0).getCodigoPedido() : "Sem pedido"
                });
            }
        } else if (resultado instanceof Entrega) {
            Entrega e = (Entrega) resultado;
            quantidadeEntregas = 1;
            model.addRow(new Object[] {
                    e.getCodigoEntrega(),
                    e.getDataEntrega(),
                    e.getEndereco(),
                    e.getEntregador() != null ? e.getEntregador().getNome() : "Sem entregador",
                    !e.getPedidos().isEmpty() ? e.getPedidos().get(0).getCodigoPedido() : "Sem pedido"
            });
        }
        label.setText("Resultados: " + quantidadeEntregas + " entrega(s)");
    }

    private void resetarConsulta() {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        label.setText("A consulta foi restaurada automaticamente após 60 segundos.");
    }


    public static void main(String[] args) {
        new TelaConsulta();
    }
}