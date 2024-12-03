package appswing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ListagemFrame extends JFrame {
    private JTextArea taListagem;

    public ListagemFrame() {
        setTitle("Listagem");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        taListagem = new JTextArea();
        taListagem.setEditable(false);
        add(new JScrollPane(taListagem), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton btnListarPedidos = new JButton("Listar Pedidos");
        panel.add(btnListarPedidos);

        JButton btnListarEntregadores = new JButton("Listar Entregadores");
        panel.add(btnListarEntregadores);

        JButton btnListarEntregas = new JButton("Listar Entregas");
        panel.add(btnListarEntregas);

        add(panel, BorderLayout.SOUTH);

        btnListarPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taListagem.setText("Listando Pedidos...");
            }
        });

        btnListarEntregadores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taListagem.setText("Listando Entregadores...");
            }
        });

        btnListarEntregas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taListagem.setText("Listando Entregas...");
            }
        });
    }
}
