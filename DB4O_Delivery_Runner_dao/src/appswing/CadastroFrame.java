package appswing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CadastroFrame extends JFrame {
    private JTextField tfIdPedido, tfDataPedido, tfValor, tfDescricao;
    private JTextField tfNomeEntregador, tfIdEntrega, tfDataEntrega, tfEndereco, tfNomeEntregadorEntrega, tfIdPedidoEntrega;
    private JButton btnCadastrarPedido, btnCadastrarEntregador, btnCadastrarEntrega;

    public CadastroFrame() {
        setTitle("Cadastro");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        
        panel.add(new JLabel("ID Pedido:"));
        tfIdPedido = new JTextField();
        panel.add(tfIdPedido);

        panel.add(new JLabel("Data Pedido (dd/MM/yyyy):"));
        tfDataPedido = new JTextField();
        panel.add(tfDataPedido);

        panel.add(new JLabel("Valor:"));
        tfValor = new JTextField();
        panel.add(tfValor);

        panel.add(new JLabel("Descrição:"));
        tfDescricao = new JTextField();
        panel.add(tfDescricao);

        btnCadastrarPedido = new JButton("Cadastrar Pedido");
        panel.add(btnCadastrarPedido);
        
      
        panel.add(new JLabel("Nome do Entregador:"));
        tfNomeEntregador = new JTextField();
        panel.add(tfNomeEntregador);
        
        btnCadastrarEntregador = new JButton("Cadastrar Entregador");
        panel.add(btnCadastrarEntregador);
        
      
        panel.add(new JLabel("ID Entrega:"));
        tfIdEntrega = new JTextField();
        panel.add(tfIdEntrega);

        panel.add(new JLabel("Data Entrega (dd/MM/yyyy):"));
        tfDataEntrega = new JTextField();
        panel.add(tfDataEntrega);

        panel.add(new JLabel("Endereço:"));
        tfEndereco = new JTextField();
        panel.add(tfEndereco);

        panel.add(new JLabel("Nome Entregador:"));
        tfNomeEntregadorEntrega = new JTextField();
        panel.add(tfNomeEntregadorEntrega);

        panel.add(new JLabel("ID Pedido:"));
        tfIdPedidoEntrega = new JTextField();
        panel.add(tfIdPedidoEntrega);

        btnCadastrarEntrega = new JButton("Cadastrar Entrega");
        panel.add(btnCadastrarEntrega);

        add(panel, BorderLayout.CENTER);

       
        btnCadastrarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              
                System.out.println("Cadastrar Pedido");
            }
        });

        btnCadastrarEntregador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cadastrar Entregador");
            }
        });

        btnCadastrarEntrega.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cadastrar Entrega");
            }
        });
    }
}
