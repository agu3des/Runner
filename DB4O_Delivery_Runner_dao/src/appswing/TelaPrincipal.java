package appswing;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TelaPrincipal {

	private JFrame frame;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JButton btnNewButton_4;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public TelaPrincipal() {
		initialize();
	}


	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 320, 390);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Pedido");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaPedido();
			}
		});
		btnNewButton.setBounds(88, 25, 112, 44);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("Entregador");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntregador();
			}
		});
		btnNewButton_1.setBounds(88, 92, 112, 44);
		frame.getContentPane().add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Entrega");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaEntrega();
			}
		});
		btnNewButton_2.setBounds(88, 159, 112, 44);
		frame.getContentPane().add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("Consultas");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaConsulta();
			}
		});
		btnNewButton_3.setBounds(88, 224, 112, 44);
		frame.getContentPane().add(btnNewButton_3 	);
		
		btnNewButton_4 = new JButton("Alterar");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaAlterar();
			}
		});
		btnNewButton_4.setBounds(88, 285, 112, 44);
		frame.getContentPane().add(btnNewButton_4);
		
	}
}
