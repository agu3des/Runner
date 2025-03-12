package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
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
import modelo.Entregador;
import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaEntrega {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonApagar;
    private JTextField textFieldCodigoEntrega, textFieldDataEntrega, textFieldEndereco, textFieldEntregador, textFieldPedido;
    private JLabel labelStatus;

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
                try {
                    Fachada.inicializar();
                    listarEntregas();
                } catch (Exception e) {
                    System.out.println("Erro ao abrir janela: " + e.getMessage());
                }
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    Fachada.finalizar();
                } catch (Exception ex) {
                    System.out.println("Erro ao fechar janela: " + ex.getMessage());
                }
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 63, 685, 155);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        JLabel labelEscolhaOpcao = new JLabel("Escolha uma opção");
        labelEscolhaOpcao.setBounds(21, 27, 200, 20);
        labelEscolhaOpcao.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.getContentPane().add(labelEscolhaOpcao);

        buttonCriar = new JButton("Criar");
        buttonCriar.setBounds(21, 400, 150, 30);
        buttonCriar.addActionListener(this::criarEntrega);
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 400, 150, 30);
        buttonBuscar.addActionListener(this::buscarEntrega);
        frame.getContentPane().add(buttonBuscar);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(341, 400, 150, 30);
        buttonApagar.addActionListener(this::apagarEntrega);
        frame.getContentPane().add(buttonApagar);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(21, 440, 677, 14);
        frame.getContentPane().add(labelStatus);

        textFieldCodigoEntrega = new JTextField();
        textFieldCodigoEntrega.setBounds(21, 250, 150, 25);
        frame.getContentPane().add(textFieldCodigoEntrega);

        textFieldDataEntrega = new JTextField();
        textFieldDataEntrega.setBounds(181, 250, 150, 25);
        frame.getContentPane().add(textFieldDataEntrega);

        textFieldEndereco = new JTextField();
        textFieldEndereco.setBounds(341, 250, 150, 25);
        frame.getContentPane().add(textFieldEndereco);

        textFieldEntregador = new JTextField();
        textFieldEntregador.setBounds(501, 250, 150, 25);
        frame.getContentPane().add(textFieldEntregador);

        textFieldPedido = new JTextField();
        textFieldPedido.setBounds(21, 280, 150, 25);
        frame.getContentPane().add(textFieldPedido);

        listarEntregas();
        frame.setVisible(true);
    }

    private void criarEntrega(ActionEvent e) {
        try {
            String codigo = textFieldCodigoEntrega.getText().trim();
            LocalDate dataEntrega = LocalDate.parse(textFieldDataEntrega.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endereco = textFieldEndereco.getText().trim();
            Entregador entregador = Fachada.localizarEntregador(textFieldEntregador.getText().trim());
            Pedido pedido = Fachada.localizarPedido(textFieldPedido.getText().trim());

            Fachada.criarEntrega(codigo, dataEntrega, endereco, entregador, pedido);
            labelStatus.setText("Entrega criada com sucesso!");
            listarEntregas();
        } catch (Exception ex) {
            System.out.println("Erro ao criar entrega: " + ex.getMessage());
            labelStatus.setText("Erro ao criar entrega: " + ex.getMessage());
        }
    }

    private void buscarEntrega(ActionEvent e) {
        try {
            Entrega entrega = Fachada.localizarEntrega(textFieldCodigoEntrega.getText().trim());
            if (entrega != null) {
                textFieldDataEntrega.setText(entrega.getDataEntrega().toString());
                textFieldEndereco.setText(entrega.getEndereco());
                textFieldEntregador.setText(entrega.getEntregador().getNome());
                textFieldPedido.setText(entrega.getPedidos().toString());
                labelStatus.setText("Entrega encontrada!");
            } else {
                labelStatus.setText("Entrega não encontrada.");
            }
        } catch (Exception ex) {
            labelStatus.setText("Erro ao buscar entrega: " + ex.getMessage());
        }
    }

    private void apagarEntrega(ActionEvent e) {
        try {
            Fachada.excluirEntrega(textFieldCodigoEntrega.getText().trim());
            labelStatus.setText("Entrega apagada com sucesso!");
            listarEntregas();
        } catch (Exception ex) {
            labelStatus.setText("Erro ao apagar entrega: " + ex.getMessage());
        }
    }

    private void listarEntregas() {
        try {
            List<Entrega> lista = Fachada.listarEntregas();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("Código");
            model.addColumn("Data");
            model.addColumn("Endereço");
            model.addColumn("Entregador");
            model.addColumn("Pedido");

            for (Entrega e : lista) {
                model.addRow(new Object[]{e.getCodigoEntrega(), e.getDataEntrega(), e.getEndereco(), e.getEntregador().getNome(), e.getPedidos().toString()});
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar entregas: " + e.getMessage());
            labelStatus.setText("Erro ao listar entregas: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaEntrega();
    }
}
