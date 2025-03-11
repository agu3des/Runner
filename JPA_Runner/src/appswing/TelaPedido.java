package appswing;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

import modelo.Pedido;
import regras_negocio.Fachada;

public class TelaPedido {
    private JDialog frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton buttonCriar, buttonBuscar, buttonBuscarValor, buttonApagar;
    private JTextField textFieldCodigoPedido, textFieldDataPedido, textFieldValor, textFieldDescricao;
    private JLabel labelStatus;
    private JLabel labelEscolhaOpcao;
	private Timer timer;

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
        
        frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				Fachada.inicializar();
				timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String titulo = "Alunos - "
								+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						frame.setTitle(titulo);
				        listarPedidos();
					}
				});
				timer.setRepeats(true);
				timer.setDelay(3000);
				timer.start();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
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
        buttonCriar.setBounds(21, 340, 150, 30);
        buttonCriar.addActionListener(e -> criarPedido());
        frame.getContentPane().add(buttonCriar);

        buttonBuscar = new JButton("Buscar");
        buttonBuscar.setBounds(181, 340, 150, 30);
        buttonBuscar.addActionListener(e -> buscarPedido());
        frame.getContentPane().add(buttonBuscar);

        buttonBuscarValor = new JButton("Buscar por valor");
        buttonBuscarValor.setBounds(341, 340, 150, 30);
        buttonBuscarValor.addActionListener(e -> mostrarPedidosPorValor());
        frame.getContentPane().add(buttonBuscarValor);

        buttonApagar = new JButton("Apagar");
        buttonApagar.setBounds(501, 340, 150, 30);
        buttonApagar.addActionListener(e -> apagarPedido());
        frame.getContentPane().add(buttonApagar);

        labelStatus = new JLabel("");
        labelStatus.setForeground(Color.RED);
        labelStatus.setBounds(21, 372, 677, 14);
        frame.getContentPane().add(labelStatus);

        frame.setVisible(true);
    }

    private void criarPedido() {
        try {
            String codigo = textFieldCodigoPedido.getText().trim();
            LocalDate dataPedido = LocalDate.parse(textFieldDataPedido.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            double valor = Double.parseDouble(textFieldValor.getText().trim());
            String descricao = textFieldDescricao.getText().trim();
            Fachada.criarPedido(codigo, dataPedido, valor, descricao);
            labelStatus.setText("Pedido criado com sucesso!");
            listarPedidos();
        } catch (Exception e) {
            labelStatus.setText("Erro ao criar pedido: " + e.getMessage());
        }
    }

    public void listarPedidos() {
        try {
            List<Pedido> lista = Fachada.listarPedidos();
            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);
            model.addColumn("Código do Pedido");
            model.addColumn("Data do Pedido");
            model.addColumn("Valor");
            model.addColumn("Descrição");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Pedido p : lista) {
                model.addRow(new Object[]{p.getCodigoPedido(), p.getDataPedido().format(formatter), p.getValor(), p.getDescricao()});
            }
        } catch (Exception e) {
            labelStatus.setText("Erro ao listar pedidos: " + e.getMessage());
        }
    }

    private void buscarPedido() {
        try {
            Pedido pedido = Fachada.localizarPedido(textFieldCodigoPedido.getText().trim());
            if (pedido != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                textFieldDataPedido.setText(pedido.getDataPedido().format(formatter));
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Pedido p : listaPedidos) {
                model.addRow(new Object[]{p.getCodigoPedido(), p.getDataPedido().format(formatter), p.getValor(), p.getDescricao()});
            }
            labelStatus.setText("Pedidos encontrados!");
        } catch (Exception e) {
            labelStatus.setText("Erro ao buscar pedidos por valor: " + e.getMessage());
        }
    }

    private void apagarPedido() {
        try {
            Fachada.excluirPedido(textFieldCodigoPedido.getText().trim());
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
