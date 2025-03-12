package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import modelo.Entrega;
import modelo.Entregador;
import regras_negocio.Fachada;

public class TelaEntregador {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonAddEntrega, buttonUpdate, buttonDelete, buttonCreate;
    private JLabel label, label2, label3, label4;
    private JTextField textFieldName, textFieldEntrega;

    public TelaEntregador() {
        initialize();
    }

    private void initialize() {
        frame = new JDialog();
        frame.setResizable(true);
        frame.setModal(true);
        frame.setTitle("Entregador");
        frame.setBounds(100, 100, 813, 437);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent arg0) {
                Fachada.inicializar();
                listarEntregadores();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        scrollPane = new JScrollPane();
        scrollPane.setBounds(21, 39, 751, 179);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);


        table.setGridColor(Color.BLACK);
        table.setFocusable(false);
        table.setBackground(Color.WHITE);
        table.setRowSelectionAllowed(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(table);

        label = new JLabel("");
        label.setForeground(Color.RED);
        label.setBounds(21, 363, 735, 14);
        frame.getContentPane().add(label);

        label2 = new JLabel("Selecione um entregador para editar");
        label2.setBounds(21, 216, 394, 14);
        frame.getContentPane().add(label2);

        label3 = new JLabel("Nome:");
        label3.setVerticalAlignment(SwingConstants.BOTTOM);
        label3.setFont(new Font("Arial", Font.PLAIN, 11));
        label3.setBounds(21, 239, 62, 14);
        frame.getContentPane().add(label3);

        label4 = new JLabel("Nova Entrega:");
        label4.setFont(new Font("Arial", Font.PLAIN, 11));
        label4.setBounds(21, 264, 80, 14);
        frame.getContentPane().add(label4);

        textFieldName = new JTextField();
        textFieldName.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldName.setBounds(93, 236, 165, 20);
        frame.getContentPane().add(textFieldName);

        textFieldEntrega = new JTextField();
        textFieldEntrega.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldEntrega.setBounds(110, 261, 148, 20);
        frame.getContentPane().add(textFieldEntrega);

        buttonCreate = new JButton("Criar Entregador");
        buttonCreate.setBounds(274, 235, 150, 23);
        buttonCreate.addActionListener(e -> criarEntregador());
        frame.getContentPane().add(buttonCreate);

        buttonAddEntrega = new JButton("Adicionar Entrega");
        buttonAddEntrega.setBounds(274, 260, 150, 23);
        buttonAddEntrega.addActionListener(e -> associarEntregaAEntregador());
        frame.getContentPane().add(buttonAddEntrega);

        frame.setVisible(true);
    }

    private void criarEntregador() {
        try {
            String nome = textFieldName.getText().trim();
            if (nome.isEmpty()) {
                label.setText("Nome não pode estar vazio");
                return;
            }
            Fachada.criarEntregador(nome);
            label.setText("Entregador criado com sucesso!");
            listarEntregadores();
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }

    private void listarEntregadores() {
        try {
            List<Entregador> lista = Fachada.listarEntregadores();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("ID do Entregador");
            model.addColumn("Nome");
            model.addColumn("Número de Entregas");

            for (Entregador e : lista) {
                model.addRow(new Object[]{e.getId(), e.getNome(), e.getEntregas().size()});
            }
        } catch (Exception e) {
            label.setText("Erro ao listar entregadores: " + e.getMessage());
        }
    }


    private void associarEntregaAEntregador() {
        try {
            String nome = textFieldName.getText().trim();
            String entregaCodigo = textFieldEntrega.getText().trim();
            if (nome.isEmpty() || entregaCodigo.isEmpty()) {
                label.setText("Nome ou entrega vazios");
                return;
            }

            Entregador entregador = Fachada.localizarEntregador(nome);
            Entrega entrega = Fachada.localizarEntrega(entregaCodigo);

            if (entregador != null && entrega != null) {
                // Chama o método para associar entrega a entregador, já tratando o limite de entregas
                Fachada.alterarEntregadorDeEntrega(entregaCodigo, nome);  
                label.setText("Entrega associada com sucesso!");
                listarEntregadores();
            } else {
                label.setText("Entregador ou entrega inválidos!");
            }
        } catch (Exception e) {
            label.setText(e.getMessage());
        }
    }
}
