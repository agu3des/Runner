package appswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.*;

import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaPedido {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonAtualizar, buttonApagar;
    private JTextField textFieldIdPedido, textFieldDataPedido, textFieldValor, textFieldDescricao;
    private JLabel labelStatus;
    private JLabel labelIdPedido, labelDataPedido, labelValor, labelDescricao;
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

        // Configuração da tabela
        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 63, 685, 155);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        // Mensagem inicial
        labelEscolhaOpcao = new JLabel("Escolha uma opção");
        labelEscolhaOpcao.setBounds(21, 27, 200, 20);
        labelEscolhaOpcao.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.getContentPane().add(labelEscolhaOpcao);

        // Botões com o mesmo tamanho e centralizados
        buttonCriar = new JButton("Criar");
        buttonCriar.setBounds(21, 340, 150, 30);
        buttonCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposCriar();
                labelEscolhaOpcao.setVisible(false);
                criarPedido(); // Chama o método para criar o pedido
            }
        });
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 340, 150, 30);
        buttonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposBuscar();
                labelEscolhaOpcao.setVisible(false);
                buscarPedido(); // Chama o método para buscar o pedido
            }
        });
        frame.getContentPane().add(buttonBuscar);

        buttonAtualizar = new JButton("Buscar por valor");
        buttonAtualizar.setBounds(341, 340, 150, 30);
        buttonAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposBuscarPorValor();
                labelEscolhaOpcao.setVisible(false);
                mostrarPedidosPorValor(); // Chama o método para buscar pedidos por valor
            }
        });
        frame.getContentPane().add(buttonAtualizar);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(501, 340, 150, 30);
        buttonApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposApagar();
                labelEscolhaOpcao.setVisible(false);
                apagarPedido(); // Chama o método para apagar o pedido
            }
        });
        frame.getContentPane().add(buttonApagar);

        // Campos de texto (inicialmente ocultos)
        labelIdPedido = new JLabel("ID do Pedido:");
        labelIdPedido.setBounds(21, 235, 100, 20);
        frame.getContentPane().add(labelIdPedido);

        textFieldIdPedido = new JTextField();
        textFieldIdPedido.setBounds(101, 235, 165, 20);
        frame.getContentPane().add(textFieldIdPedido);

        labelDataPedido = new JLabel("Data do Pedido:");
        labelDataPedido.setBounds(21, 260, 100, 20);
        frame.getContentPane().add(labelDataPedido);

        textFieldDataPedido = new JTextField();
        textFieldDataPedido.setBounds(101, 260, 165, 20);
        frame.getContentPane().add(textFieldDataPedido);

        labelValor = new JLabel("Valor:");
        labelValor.setBounds(21, 285, 100, 20);
        frame.getContentPane().add(labelValor);

        textFieldValor = new JTextField();
        textFieldValor.setBounds(101, 285, 165, 20);
        frame.getContentPane().add(textFieldValor);

        labelDescricao = new JLabel("Descrição:");
        labelDescricao.setBounds(21, 310, 100, 20);
        frame.getContentPane().add(labelDescricao);

        textFieldDescricao = new JTextField();
        textFieldDescricao.setBounds(101, 310, 165, 20);
        frame.getContentPane().add(textFieldDescricao);

        // Inicialmente, ocultar os campos
        ocultarCampos();

        // Label de status
        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(21, 372, 607, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    // Função para listar os pedidos
    public void listarPedidos() {
        List<Pedido> lista = Fachada.listarPedidos();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("ID Pedido");
        model.addColumn("Data Pedido");
        model.addColumn("Valor");
        model.addColumn("Descrição");

        for (Pedido p : lista) {
            model.addRow(new Object[] { p.getIdPedido(), p.getDataPedido(), p.getValor(), p.getDescricao() });
        }
    }

    private void mostrarCamposCriar() {
        ocultarCampos();
        labelIdPedido.setVisible(true);
        textFieldIdPedido.setVisible(true);
        labelDataPedido.setVisible(true);
        textFieldDataPedido.setVisible(true);
        labelValor.setVisible(true);
        textFieldValor.setVisible(true);
        labelDescricao.setVisible(true);
        textFieldDescricao.setVisible(true);
    }

    private void mostrarCamposBuscar() {
        ocultarCampos();
        labelIdPedido.setVisible(true);
        textFieldIdPedido.setVisible(true);
    }

    private void mostrarCamposBuscarPorValor() {
        ocultarCampos();
        labelValor.setVisible(true);
        textFieldValor.setVisible(true);
    }

    private void mostrarCamposApagar() {
        ocultarCampos();
        labelIdPedido.setVisible(true);
        textFieldIdPedido.setVisible(true);
    }

    private void ocultarCampos() {
        labelIdPedido.setVisible(false);
        textFieldIdPedido.setVisible(false);
        labelDataPedido.setVisible(false);
        textFieldDataPedido.setVisible(false);
        labelValor.setVisible(false);
        textFieldValor.setVisible(false);
        labelDescricao.setVisible(false);
        textFieldDescricao.setVisible(false);
    }

    private void criarPedido() {
        try {
            String idPedido = textFieldIdPedido.getText().trim();
            String dataPedido = textFieldDataPedido.getText().trim();
            double valor = Double.parseDouble(textFieldValor.getText().trim());
            String descricao = textFieldDescricao.getText().trim();

            Fachada.criarPedido(idPedido, dataPedido, valor, descricao);
            labelStatus.setText("Pedido criado com sucesso!");
            listarPedidos();
        } catch (Exception e) {
            labelStatus.setText("Erro ao criar pedido: " + e.getMessage());
        }
    }

    private void buscarPedido() {
        try {
            String idPedido = textFieldIdPedido.getText().trim();
            Pedido p = Fachada.localizarPedido(idPedido);
            if (p != null) {
                textFieldDataPedido.setText(p.getDataPedido());
                textFieldValor.setText(String.valueOf(p.getValor()));
                textFieldDescricao.setText(p.getDescricao());
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
                model.addRow(new Object[] { p.getIdPedido(), p.getDataPedido(), p.getValor(), p.getDescricao() });
            }

            labelStatus.setText("Pedidos encontrados!");
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar pedidos por valor: " + e.getMessage());
        }
    }

    private void apagarPedido() {
        try {
            String idPedido = textFieldIdPedido.getText().trim();
            Fachada.excluirPedido(idPedido);
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
