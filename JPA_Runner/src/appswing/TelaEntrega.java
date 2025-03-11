package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import regras_negocio.Fachada;

public class TelaEntrega {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonAtualizar, buttonApagar;
    private JTextField textFieldCodigoEntrega, textFieldDataEntrega, textFieldValor, textFieldEndereco;
    private JLabel labelStatus;
    private JLabel labelEscolhaOpcao;

    public TelaEntrega() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setTitle("Entregas");
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
        buttonCriar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                criarEntrega();
            }
        });
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 340, 150, 30);
        buttonBuscar.addActionListener(e -> buscarEntrega());
        frame.getContentPane().add(buttonBuscar);


        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(501, 340, 150, 30);
        buttonApagar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                apagarEntrega();
            }
        });
        frame.getContentPane().add(buttonApagar);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(21, 372, 677, 14);
        frame.getContentPane().add(labelStatus);

        listarEntregas();
        frame.setVisible(true);
    }

    private Object criarEntrega() {
        try {
            String codigo = textFieldCodigoEntrega.getText().trim();
            LocalDate dataEntrega = textFieldDataEntrega.getText().trim();
            double valor = Double.parseDouble(textFieldValor.getText().trim());
            String endereco = textFieldEndereco.getText().trim();
            Fachada.criarEntrega(codigo, dataEntrega, valor, endereco);
            labelStatus.setText("Entregador criado com sucesso!");
            listarEntregas();
        } catch (Exception e) {
            labelStatus.setText("Erro ao criar entregador: " + e.getMessage());
        }
    }

    public void listarEntregas() {
        try {
            List<Entrega> lista = regras_negocio.Fachada.listarEntregas();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("Código Entrega");
            model.addColumn("Data Entrega");
            model.addColumn("Valor");
            model.addColumn("Descrição");

            for (Entrega e : lista) {
                model.addRow(new Object[]{e.getId(), e.getDataEntrega(), e.getEndereco()});
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar entregas: " + e.getMessage());
        }
    }
    private void buscarEntrega() {
        try {
            Entrega entrega = regras_negocio.Fachada.localizarEntrega(textFieldCodigoEntrega.getText().trim()); 
            if (entrega != null) {
                // Convertendo LocalDateTime para String no formato correto
                textFieldDataEntrega.setText(entrega.getDataEntrega().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                textFieldEndereco.setText(entrega.getEndereco());
                labelStatus.setText("Entrega encontrado!");
            } else {
                labelStatus.setText("Entrega não encontrado.");
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar entrega: " + e.getMessage());
        }
    }


    private void apagarEntrega() {
        try {
            regras_negocio.Fachada.excluirEntrega(textFieldCodigoEntrega.getText().trim());
            labelStatus.setText("Entrega apagado com sucesso!");
            listarEntregas();
        } catch (Exception e) {
            labelStatus.setText("Erro ao apagar entrega: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TelaEntrega();
    }
}