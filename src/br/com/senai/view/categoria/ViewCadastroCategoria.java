package br.com.senai.view.categoria;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.service.CategoriaService;

public class ViewCadastroCategoria extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField edtNome;

	private CategoriaService service ;
	private Categoria categoria ;
	
	/**
	 * Create the frame.
	 */
	public ViewCadastroCategoria() {
		
		this.service = new CategoriaService();
				
		setTitle("Gerencia Categorias - Cadastro");
		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 172);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ViewConsultaCategoria view = new ViewConsultaCategoria();
				view.setVisible(true);
				dispose();
				
				
				
			}
		});
		btnPesquisar.setBounds(325, 13, 99, 26);
		contentPane.add(btnPesquisar);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.BOLD, 12));
		lblNome.setBounds(27, 55, 55, 16);
		contentPane.add(lblNome);
		
		edtNome = new JTextField();
		edtNome.setBounds(71, 52, 353, 20);
		contentPane.add(edtNome);
		edtNome.setColumns(10);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edtNome.setText("");
				
			}
		});
		btnCancelar.setBounds(325, 82, 99, 26);
		contentPane.add(btnCancelar);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String nome = edtNome.getText();
					
					if (categoria == null) {
					
						categoria = new Categoria(nome);
					
					}else {
						categoria.setNome(nome);
					
					}
					service.salvar(categoria);
					JOptionPane.showMessageDialog(contentPane,"Categoria salva com sucesso ");
 					
				} catch (Exception ex) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
					
				}
				
			}
		});
		btnSalvar.setBounds(209, 82, 99, 26);
		contentPane.add(btnSalvar);
	}
	public void SetCategoria(Categoria categoria ) {
		this.categoria = categoria;
		this.edtNome.setText(categoria.getNome());
	}
}
