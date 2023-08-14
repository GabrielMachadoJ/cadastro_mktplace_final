package br.com.senai.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import br.com.senai.view.categoria.ViewConsultaCategoria;
import br.com.senai.view.horario.ViewCadastroHorarios;
import br.com.senai.view.restaurante.ViewConsultaRestaurante;

public class ViewMenu extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMenu frame = new ViewMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewMenu() {
		setAutoRequestFocus(false);
		setBackground(UIManager.getColor("List.dropCellBackground"));
		setType(Type.UTILITY);
		setForeground(new Color(0, 0, 0));
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Tela Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 597, 400);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 573, 23);
		contentPane.add(menuBar);
		
		JMenu mnCadastros = new JMenu("Cadastros");
		menuBar.add(mnCadastros);
		
		JMenuItem btnCadastro = new JMenuItem("Categorias");
		btnCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaCategoria view = new ViewConsultaCategoria();
				view.setVisible(true);
				
			}
		});
		mnCadastros.add(btnCadastro);
		
		JMenuItem btnRestaurante = new JMenuItem("Restaurantes");
		btnRestaurante.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaRestaurante view = new ViewConsultaRestaurante();
				view.setVisible(true);
				
			}
		});
		mnCadastros.add(btnRestaurante);
		
		JMenu config = new JMenu("Configuração");
		config.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(config);
		
		JMenuItem btnHorarios = new JMenuItem("Horários");
		
		btnHorarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewCadastroHorarios view = new ViewCadastroHorarios();
				view.setVisible(true);
				
			}
		});
		config.add(btnHorarios);
		
		JMenu Sair = new JMenu("Sair");
		menuBar.add(Sair);
		
		JMenuItem btnSair = new JMenuItem("Sair");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				dispose();
				
			}
		});
		Sair.add(btnSair);
		
		setLocationRelativeTo(null);

	}
}
