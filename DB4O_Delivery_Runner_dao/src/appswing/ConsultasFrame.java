package appswing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConsultasFrame extends JFrame {
    private JTextField tfValorPedido, tfNEntregas, tfDataEntrega;
    private JTextArea taResultados;

    public ConsultasFrame() {
        setTitle("Consultas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("Valor do Pedido:"));
        tfValorPedido = new JTextField();
        panel.add(tfValorPedido);

        panel.add(new JLabel("Número de Entregas:"));
        tfNEntregas = new JTextField();
        panel.add(tfNEntregas);

        panel.add(new JLabel("Data da Entrega (dd/MM/yyyy):"));
        tfDataEntrega = new JTextField();
        panel.add(tfDataEntrega);

        JButton btnConsultar = new JButton("Consultar");
        panel.add(btnConsultar);

        taResultados = new JTextArea();
        taResultados.setEditable(false);
        add(new JScrollPane(taResultados), BorderLayout.CENTER);

        add(panel, BorderLayout.NORTH);

        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taResultados.setText("Consultando...");
            }
        });
    }
}
