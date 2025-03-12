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
    private JTextField codigoEntregaTextField, dataEntregaTextField, enderecoTextField, entregadorTextField, pedidoTextField;
    private JLabel labelStatus;
    private JLabel labelEscolhaOpcao;

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
            List<Entrega> lista = Fachada.listarEntregas();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("Código");
            model.addColumn("Data");
            model.addColumn("Endereço");
            model.addColumn("Entregador");
            model.addColumn("Pedido");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Entrega e : lista) {
                model.addRow(new Object[]{e.getCodigoEntrega(), e.getDataEntrega().format(formatter), e.getEndereco(), e.getEntregador().getNome(), e.getPedidos().toString()});
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar entregas: " + e.getMessage());
        }
    }

    private void buscarEntrega(ActionEvent e) {
        try {
            Entrega entrega = Fachada.localizarEntrega(codigoEntregaTextField.getText().trim());
            if (entrega != null) {
                dataEntregaTextField.setText(entrega.getDataEntrega().toString());
                enderecoTextField.setText(entrega.getEndereco());
                entregadorTextField.setText(entrega.getEntregador().getNome());
                pedidoTextField.setText(entrega.getPedidos().toString());
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
