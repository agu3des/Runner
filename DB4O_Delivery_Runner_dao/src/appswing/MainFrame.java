package appswing;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    private JButton btnCadastro, btnListagem, btnConsultas;
    
    public MainFrame() {
        setTitle("Sistema de Entregas");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        btnCadastro = new JButton("Cadastro");
        btnListagem = new JButton("Listagem");
        btnConsultas = new JButton("Consultas");
        
       
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        panel.add(btnCadastro);
        panel.add(btnListagem);
        panel.add(btnConsultas);
        
        
        add(panel);
        
        
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroFrame().setVisible(true);  
            }
        });
        
        btnListagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ListagemFrame().setVisible(true);  
            }
        });
        
        btnConsultas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultasFrame().setVisible(true);  
            }
        });
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
