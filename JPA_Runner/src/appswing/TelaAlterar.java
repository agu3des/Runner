package appswing;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import regras_negocio.Fachada;

public class TelaAlterar {
    private JDialog frame;
    private JTextField textField1;
    private JTextField textField2;
    private JLabel label;
    private JLabel label1;
    private JLabel label2;
    private JButton button;
    private JComboBox<String> comboBox;
    
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_4;

    public TelaAlterar() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize() {
        frame = new JDialog();
        frame.setModal(true);
        frame.setResizable(false);
        frame.setTitle("Alterar Dados");
        frame.setBounds(100, 100, 600, 350);
        frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                Fachada.inicializar();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                Fachada.finalizar();
            }
        });

        JLabel lblOpcao = new JLabel("Escolha a alteração:");
        lblOpcao.setBounds(20, 31, 200, 20);
        frame.getContentPane().add(lblOpcao);

        comboBox = new JComboBox<>(new String[] {
                "Alterar entregador de entrega",
                "Alterar endereço de entrega",
                "Alterar data de entrega",
                "Alterar nome do entregador"
        });
        comboBox.setBounds(20, 50, 540, 30);
        frame.getContentPane().add(comboBox);

        label1 = new JLabel("Parâmetro 1:");
        label1.setBounds(20, 90, 250, 20);
        frame.getContentPane().add(label1);
        
        textField1 = new JTextField();
        textField1.setBounds(20, 110, 540, 30);
        frame.getContentPane().add(textField1);
        
        label2 = new JLabel("Parâmetro 2:");
        label2.setBounds(20, 150, 250, 20);
        frame.getContentPane().add(label2);
        
        textField2 = new JTextField();
        textField2.setBounds(20, 170, 540, 30);
        frame.getContentPane().add(textField2);

        button = new JButton("Alterar");
        button.setFont(new Font("Tahoma", Font.PLAIN, 12));
        button.setBounds(220, 220, 120, 30);
        frame.getContentPane().add(button);
        
		btnNewButton = new JButton("Pedido");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaPedido();
			}
		});
		btnNewButton.setBounds(20, 10, 112, 14);
		frame.getContentPane().add(btnNewButton);
        
		btnNewButton_1 = new JButton("Entrega");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntrega();
			}
		});
		btnNewButton_1.setBounds(152, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Entregador");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntregador();
			}
		});
		btnNewButton_2.setBounds(291, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_4 = new JButton("Consultas");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaConsulta();
			}
		});
		btnNewButton_4.setBounds(423, 10, 112, 14);
		frame.getContentPane().add(btnNewButton_4);
        

        label = new JLabel("");
        label.setBounds(20, 260, 540, 20);
        frame.getContentPane().add(label);

        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                switch (index) {
                    case 0:
                        label1.setText("Código da entrega:");
                        label2.setText("Novo entregador:");
                        break;
                    case 1:
                        label1.setText("Código da entrega:");
                        label2.setText("Novo endereço:");
                        break;
                    case 2:
                        label1.setText("Código da entrega:");
                        label2.setText("Nova data (dd/MM/yyyy):");
                        break;
                    case 3:
                        label1.setText("Nome atual do entregador:");
                        label2.setText("Novo nome:");
                        break;
                }
            }
        });

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                String input1 = textField1.getText().trim();
                String input2 = textField2.getText().trim();

                if (input1.isEmpty() || input2.isEmpty()) {
                    label.setText("Por favor, preencha ambos os campos.");
                    return;
                }

                try {
                    switch (index) {
                        case 0:
                            Fachada.alterarEntregadorDeEntrega(input1, input2);
                            label.setText("Entregador alterado com sucesso!");
                            break;
                        case 1:
                            Fachada.alterarEnderecoEntrega(input1, input2);
                            label.setText("Endereço alterado com sucesso!");
                            break;
                        case 2:
                            LocalDate data = LocalDate.parse(input2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            Fachada.alterarDataEntrega(input1, data);
                            label.setText("Data alterada com sucesso!");
                            break;
                        case 3:
                            Fachada.alterarNomeEntregador(input1, input2);
                            label.setText("Nome do entregador alterado com sucesso!");
                            break;
                    }
                } catch (Exception ex) {
                    label.setText("Erro: " + ex.getMessage());
                }
            }
        });
    }
}
