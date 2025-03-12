package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.*;
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
        scrollPane.setBounds(20, 100, 750, 200);
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
        label.setBounds(20, 320, 750, 20);
        frame.getContentPane().add(label);

        label_4 = new JLabel("Resultados:");
        label_4.setBounds(20, 280, 750, 20);
        frame.getContentPane().add(label_4);

        button = new JButton("Consultar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBounds(650, 10, 120, 30);
        frame.getContentPane().add(button);

        comboBox = new JComboBox<>(new String[] {
                "Consultar pedidos pelo código",
                "Consultar entregadores pelo nome",
                "Consultar entregas pelo código",
                "Consultar entregas pela data",
                "Consultar entregadores com n entregas",
                "Consultar pedidos pelo valor"
        });
        comboBox.setBounds(20, 10, 600, 30);
        frame.getContentPane().add(comboBox);

        textField = new JTextField();
        textField.setBounds(20, 50, 600, 30);
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
                            listarPedidos(Fachada.consultarPedidos(input));
                            break;
                        case 1:
                            listarEntregadores(Fachada.consultarEntregadores(input));
                            break;
                        case 2:
                            listarEntregas(Fachada.consultarEntregas(input));
                            break;
                        case 3:
                            listarEntregas(Fachada.consultarEntregaPorData(input));
                            break;
                        case 4:
                            listarEntregadores(Fachada.consultarPorNEntregas(Integer.parseInt(input)));
                            break;
                        case 5:
                            listarPedidos(Fachada.consultarPedidoPorValor(Double.parseDouble(input)));
                            break;
                    }
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
    }

    private void listarPedidos(List<Pedido> lista) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Código");
        model.addColumn("Data");
        model.addColumn("Valor");
        model.addColumn("Descrição");
        for (Pedido p : lista) {
            model.addRow(new Object[] { p.getId(), p.getDataPedido(), p.getValor(), p.getDescricao() });
        }
    }

    private void listarEntregadores(List<Entregador> lista) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Nome");
        model.addColumn("Número de Entregas");
        for (Entregador e : lista) {
            model.addRow(new Object[] { e.getNome(), e.getEntregas().size() });
        }
    }

    private void listarEntregas(List<Entrega> lista) {
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Código");
        model.addColumn("Data");
        model.addColumn("Endereço");
        model.addColumn("Entregador");
        model.addColumn("Id do Pedido");
        for (Entrega e : lista) {
            model.addRow(new Object[] {
                    e.getId(),
                    e.getdataEntrega(),
                    e.getEndereco(),
                    e.getEntregador() != null ? e.getEntregador().getNome() : "Sem entregador",
                    !e.getPedidos().isEmpty() ? e.getPedidos().get(0).getId() : "Sem pedido"
            });
        }
    }

    public static void main(String[] args) {
        new TelaConsulta();
    }
}