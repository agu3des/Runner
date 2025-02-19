package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
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
        scrollPane.setBounds(20, 50, 750, 200);
        frame.getContentPane().add(scrollPane);

        table = new JTable() {
            public boolean isCellEditable(int rowIndex, int vColIndex) {
                return false;
            }
        };

        table.setGridColor(Color.BLACK);
        table.setRequestFocusEnabled(false);
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

        label = new JLabel(""); // Label para mensagens
        label.setForeground(Color.BLUE);
        label.setBounds(20, 320, 750, 20);
        frame.getContentPane().add(label);

        label_4 = new JLabel("Resultados:");
        label_4.setBounds(20, 260, 750, 20);
        frame.getContentPane().add(label_4);

        button = new JButton("Consultar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                if (index < 0)
                    label_4.setText("Consulta não selecionada");
                else {
                    label_4.setText("");
                    try {
                        switch (index) {
                            case 0: // Consultar pedidos que tenham "17" no ID
                                listarPedidos(Fachada.consultarPedidos("17"));
                                break;
                            case 1: // Consultar entregadores com "An" no nome
                                listarEntregadores(Fachada.consultarEntregadores("An"));
                                break;
                            case 2: // Consultar entregas que tenham "1" no ID
                                listarEntregas(Fachada.consultarEntregas("1"));
                                break;
                            case 3: // Consultar entregas por data
                                listarEntregas(Fachada.consultarEntregaPorData("06/12/2024"));
                                break;
                            case 4: // Consultar entregadores com mais de N entregas
                                listarEntregadores(Fachada.consultarPorNEntregas(1));
                                break;
                            case 5: // Consultar pedidos por valor
                                listarPedidos(Fachada.consultarPedidoPorValor(100.00));
                                break;
                        }
                    } catch (Exception ex) {
                        label.setText(ex.getMessage());
                    }
                }
            }
        });
        button.setBounds(650, 10, 120, 30);
        frame.getContentPane().add(button);

        comboBox = new JComboBox<>();
        comboBox.setToolTipText("Selecione a consulta");
        comboBox.setModel(new DefaultComboBoxModel<>(
                new String[] { 
                    "Pedidos com '17' no ID", 
                    "Entregadores com 'An' no nome",
                    "Entregas com '1' no ID", 
                    "Entregas na data 06/12/2024", 
                    "Entregadores com mais de 1 entrega",
                    "Pedidos com valor 100.00"
                }));
        comboBox.setBounds(20, 10, 600, 30);
        frame.getContentPane().add(comboBox);
    }

    private void listarPedidos(List<Pedido> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Data");
            model.addColumn("Valor");
            model.addColumn("Descrição");

            for (Pedido p : lista) {
                model.addRow(new Object[] { p.getIdPedido(), p.getDataPedido(), p.getValor(), p.getDescricao() });
            }
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }

    private void listarEntregadores(List<Entregador> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("Nome");
            model.addColumn("Número de Entregas");

            for (Entregador e : lista) {
                model.addRow(new Object[] { e.getNome(), e.getEntregas().size() });
            }
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }

    private void listarEntregas(List<Entrega> lista) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            model.addColumn("ID");
            model.addColumn("Data");
            model.addColumn("Endereço");
            model.addColumn("Entregador");
            model.addColumn("Id do Pedido");

            for (Entrega e : lista) {
                model.addRow(new Object[] { 
                    e.getIdEntrega(), 
                    e.getdataEntrega(), 
                    e.getEndereco(), 
                    e.getEntregador() != null ? e.getEntregador().getNome() : "Sem entregador", 
                    e.getPedido() != null ? e.getPedido().getIdPedido() : "Sem pedido" 
                });
            }
        } catch (Exception erro) {
            label.setText(erro.getMessage());
        }
    }
    
    public static void main(String[] args) {
        // Inicia a interface gráfica
        new TelaConsulta();
    }
}