package appswing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import modelo.Entregador;
import regras_negocio.Fachada;

public class TelaEntregador {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonAlterar, buttonApagar;
    private JTextField textFieldNome, textFieldNovoNome;
    private JLabel labelStatus;
    private JLabel labelNome, labelNovoNome;
    private JLabel labelEscolhaOpcao;

    public TelaEntregador() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Entregadores");
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
                criarEntregador();
            }
        });
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 340, 150, 30);
        buttonBuscar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposBuscar();
                buscarEntregador();
            }
        });
        frame.getContentPane().add(buttonBuscar);

        buttonAlterar = new JButton("Alterar Nome");
        buttonAlterar.setBounds(341, 340, 150, 30);
        buttonAlterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposAlterar();
                alterarNomeEntregador();
            }
        });
        frame.getContentPane().add(buttonAlterar);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(501, 340, 150, 30);
        buttonApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarCamposApagar();
                apagarEntregador();
            }
        });
        frame.getContentPane().add(buttonApagar);

        // Campos de texto
        labelNome = new JLabel("Nome:");
        labelNome.setBounds(21, 235, 100, 20);
        frame.getContentPane().add(labelNome);

        textFieldNome = new JTextField();
        textFieldNome.setBounds(101, 235, 165, 20);
        frame.getContentPane().add(textFieldNome);

        labelNovoNome = new JLabel("Novo Nome:");
        labelNovoNome.setBounds(21, 260, 100, 20);
        frame.getContentPane().add(labelNovoNome);

        textFieldNovoNome = new JTextField();
        textFieldNovoNome.setBounds(101, 260, 165, 20);
        frame.getContentPane().add(textFieldNovoNome);

        ocultarCampos();

        // Label de status
        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(21, 372, 607, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    // Função para listar os entregadores
    public void listarEntregadores() {
        List<Entregador> lista = Fachada.listarEntregadores();
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Nome");
        model.addColumn("Quantidade de Entregas");

        for (Entregador e : lista) {
            model.addRow(new Object[] { e.getNome(), e.getEntregas().size() });
        }
    }

    private void mostrarCamposCriar() {
        ocultarCampos();
        labelNome.setVisible(true);
        textFieldNome.setVisible(true);
    }

    private void mostrarCamposBuscar() {
        ocultarCampos();
        labelNome.setVisible(true);
        textFieldNome.setVisible(true);
    }

    private void mostrarCamposAlterar() {
        ocultarCampos();
        labelNome.setVisible(true);
        textFieldNome.setVisible(true);
        labelNovoNome.setVisible(true);
        textFieldNovoNome.setVisible(true);
    }

    private void mostrarCamposApagar() {
        ocultarCampos();
        labelNome.setVisible(true);
        textFieldNome.setVisible(true);
    }

    private void ocultarCampos() {
        labelNome.setVisible(false);
        textFieldNome.setVisible(false);
        labelNovoNome.setVisible(false);
        textFieldNovoNome.setVisible(false);
    }

    private void criarEntregador() {
        try {
            String nome = textFieldNome.getText().trim();
            Fachada.criarEntregador(nome);
            labelStatus.setText("Entregador criado com sucesso!");
            listarEntregadores();
        } catch (Exception e) {
            labelStatus.setText("Erro ao criar entregador: " + e.getMessage());
        }
    }

    private void buscarEntregador() {
        try {
            String nome = textFieldNome.getText().trim();
            Entregador e = Fachada.localizarEntregador(nome);
            textFieldNovoNome.setText(e.getNome());
            labelStatus.setText("Entregador encontrado!");
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar entregador: " + e.getMessage());
        }
    }

    private void alterarNomeEntregador() {
        try {
            String nome = textFieldNome.getText().trim();
            String novoNome = textFieldNovoNome.getText().trim();
            Fachada.alterarNomeEntregador(nome, novoNome);
            labelStatus.setText("Nome do entregador alterado com sucesso!");
            listarEntregadores();
        } catch (Exception e) {
            labelStatus.setText("Erro ao alterar nome do entregador: " + e.getMessage());
        }
    }

    private void apagarEntregador() {
        try {
            String nome = textFieldNome.getText().trim();
            Fachada.excluirEntregador(nome);
            labelStatus.setText("Entregador excluído com sucesso!");
            listarEntregadores();
        } catch (Exception e) {
            labelStatus.setText("Erro ao excluir entregador: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaEntregador();
    }
}