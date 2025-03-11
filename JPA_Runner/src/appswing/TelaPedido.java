package appswing;

import java.awt.Color;
import java.awt.Font;
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
    private JButton buttonCriar, buttonBuscar, buttonAtualizar, buttonApagar;
    private JTextField textFieldIdPedido, textFieldDataPedido, textFieldValor, textFieldDescricao;
    private JLabel labelStatus;
    private JLabel labelEscolhaOpcao;

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
        buttonCriar.setBounds(21, 340, 150, 30);
        buttonCriar.addActionListener(e -> this.criarPedido());
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 340, 150, 30);
        buttonBuscar.addActionListener(e -> buscarPedido());
        frame.getContentPane().add(buttonBuscar);

        buttonAtualizar = new JButton("Buscar por valor");
        buttonAtualizar.setBounds(341, 340, 150, 30);
        buttonAtualizar.addActionListener(e -> mostrarPedidosPorValor());
        frame.getContentPane().add(buttonAtualizar);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(501, 340, 150, 30);
        buttonApagar.addActionListener(e -> apagarPedido());
        frame.getContentPane().add(buttonApagar);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(21, 372, 607, 14);
        frame.getContentPane().add(labelStatus);

        listarPedidos();
        frame.setVisible(true);
    }

    private Object criarPedido() {
        // TODO Auto-generated method stub
        return null;
    }

    public void listarPedidos() {
        try {
            List<Pedido> lista = regras_negocio.Fachada.listarPedidos();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("ID Pedido");
            model.addColumn("Data Pedido");
            model.addColumn("Valor");
            model.addColumn("Descrição");

            for (Pedido p : lista) {
                model.addRow(new Object[]{p.getId(), p.getDataPedido(), p.getValor(), p.getDescricao()});
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar pedidos: " + e.getMessage());
        }
    }
    private void buscarPedido() {
        try {
            Pedido pedido = regras_negocio.Fachada.localizarPedido(textFieldIdPedido.getText().trim()); // Método corrigido
            if (pedido != null) {
                // Convertendo LocalDateTime para String no formato correto
                textFieldDataPedido.setText(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                textFieldValor.setText(String.valueOf(pedido.getValor()));
                textFieldDescricao.setText(pedido.getDescricao());
                labelStatus.setText("Pedido encontrado!");
            } else {
                labelStatus.setText("Pedido não encontrado.");
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar pedido: " + e.getMessage());
        }
    }

    private void mostrarPedidosPorValor() {
        try {
            double valor = Double.parseDouble(textFieldValor.getText().trim());
            List<Pedido> listaPedidos = Fachada.consultarPedidoPorValor(valor);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            for (Pedido p : listaPedidos) {
                model.addRow(new Object[]{p.getId(), p.getDataPedido(), p.getValor(), p.getDescricao()});
            }
            labelStatus.setText("Pedidos encontrados!");
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar pedidos por valor: " + e.getMessage());
        }
    }

    private void apagarPedido() {
        try {
            regras_negocio.Fachada.excluirPedido(textFieldIdPedido.getText().trim());
            labelStatus.setText("Pedido apagado com sucesso!");
            listarPedidos();
        } catch (Exception e) {
            labelStatus.setText("Erro ao apagar pedido: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaPedido();
    }
}