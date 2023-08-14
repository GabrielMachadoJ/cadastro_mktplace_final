package br.com.senai.view.horario;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.HorarioService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.table.TableModelHorarios;

public class ViewCadastroHorarios extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField inputAbertura;
	private JTextField inputFechamento;
	private JTable tableHorarios;
	private JComboBox<Restaurante> listRestaurante;
	private JComboBox<String> listDiasSemana;
	private	Horario horario;
	private	HorarioService horarioService;
	private	RestauranteService restauranteService;
	private	Horario horarioSelecionado ;
			
	public boolean isAlterar = false;

	public void carregarCombo() {

		List<Restaurante> restaurante = restauranteService.listarTodos();
		listRestaurante.addItem(null);
		
		if (restaurante != null) {
			
			for (Restaurante u : restaurante) {
				
				listRestaurante.addItem(u);
				
			}
		}
	}
	
	private void nullCamp() {
		
		inputAbertura.setText("");
		inputFechamento.setText("");
		listDiasSemana.setSelectedIndex(0);

	}
	
	private void editaHorario(Horario horario) {

		this.horario = horario;
		this.inputAbertura.setText(horario.getHoraAbertura().toString());
		this.inputFechamento.setText(horario.getHoraFechamento().toString());
		this.listRestaurante.setSelectedItem(horario.getRestaurante());
		this.listDiasSemana.setSelectedItem(horario.getDiaDaSemana());

	}
	
	public void atualizaTable (Restaurante restauranteSelecionado) {
		
		List<Horario> horarios = horarioService.listarPorId(restauranteSelecionado.getId());
		TableModelHorarios modelHorarios = new TableModelHorarios(horarios);
		tableHorarios.setModel(modelHorarios);
	
	}
	

	/**
	 * Create the frame.
	 */
	public ViewCadastroHorarios() {

		setForeground(new Color(0, 0, 0));
		setFont(new Font("Arial", Font.PLAIN, 12));
		setBackground(new Color(176, 224, 230));
		setTitle("Gerenciar Horarios - Cadastro");
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 401);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Restaurante:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 28, 74, 13);
		contentPane.add(lblNewLabel);

		listRestaurante = new JComboBox<Restaurante>();
		listRestaurante.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					Restaurante restauranteSelecionado = (Restaurante) listRestaurante.getSelectedItem();

					List<Horario> horarios = horarioService.listarPorId(restauranteSelecionado.getId());

					if (horarios != null) {

						TableModelHorarios modelHorarios = new TableModelHorarios(horarios);
						tableHorarios.setModel(modelHorarios);
						 
					} else {
						throw new NullPointerException("Erro na tabela de horarios .");
					}

				} catch (Exception e2) {
					throw new NullPointerException(
							"Erro ao buscar os horarios do restaurante. Motivo:" + e2.getMessage());
				}

			}
		});
		listRestaurante.setBounds(94, 24, 482, 21);
		contentPane.add(listRestaurante);

		JLabel lblNewLabel_1 = new JLabel("Dia da Semana:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(10, 59, 91, 13);
		contentPane.add(lblNewLabel_1);

		listDiasSemana = new JComboBox<String>();

		String[] diasSemana = {"" ,"DOMINGO", "SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA","SABADO"};

		for (String dia : diasSemana) {
			listDiasSemana.addItem(dia);
		};

		listDiasSemana.setBounds(104, 55, 91, 21);
		contentPane.add(listDiasSemana);

		JLabel lblNewLabel_2 = new JLabel("Abertura:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(205, 57, 52, 17);
		try {

			MaskFormatter aberturaFormatter = new MaskFormatter("##:##");
			inputAbertura = new JFormattedTextField(aberturaFormatter);
			inputAbertura.setBounds(258, 55, 59, 19);
			contentPane.add(inputAbertura);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel lblNewLabel_3 = new JLabel("Fechamento:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(325, 59, 80, 13);
		try {

			MaskFormatter fechamentoFormatter = new MaskFormatter("##:##");
			inputFechamento = new JFormattedTextField(fechamentoFormatter);
			inputFechamento.setBounds(399, 56, 59, 19);
			contentPane.add(inputFechamento);

		} catch (ParseException e) {
			e.printStackTrace();
		}


		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnAdicionar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					
						String txtAbertura = inputAbertura.getText();
						String txtFechamento = inputFechamento.getText();
						Time maxHora =  Time.valueOf("23:59:59");
					
						Time horaAbertura = txtAbertura.replace(":"," ").trim().isEmpty() || txtAbertura.replace(":"," ").trim().isBlank() ? null : Time.valueOf(txtAbertura + ":00");
			            Time horaFechamento = txtFechamento.replace(":"," ").trim().isEmpty() || txtFechamento.replace(":"," ").trim().isBlank() ? null : Time.valueOf(txtFechamento + ":00");
 			            String diaSelecionado = (String) listDiasSemana.getSelectedItem();
			            Restaurante restauranteSelecionado = (Restaurante) listRestaurante.getSelectedItem();
			            
			            if (restauranteSelecionado == null) {
			                JOptionPane.showMessageDialog(null, "Restaurante obrigatório");
			                return;
			            }
			            
			            if (diaSelecionado == null || diaSelecionado.isBlank() || diaSelecionado.isEmpty()) {
			                JOptionPane.showMessageDialog(null, "Dia obrigatório");
			                return;
			            }


			            if (horaAbertura == null) {
			                JOptionPane.showMessageDialog(null, "Hora de abertura inválida");
			                return;
			            }

			            if (horaFechamento == null) {
			                JOptionPane.showMessageDialog(null, "Hora de fechamento inválida");
			                return;
			            }

			      
			   
						System.out.println("chegou aqui2");
						
						if(isAlterar == false ) {
								
								if ( horaAbertura.compareTo(maxHora) < 0 && horaFechamento.compareTo(maxHora) < 0 ) {
		
									Horario horario = new Horario(diaSelecionado, horaAbertura, horaFechamento, restauranteSelecionado);
									horarioService.salvar(horario);
									nullCamp();
									atualizaTable(restauranteSelecionado);
									
								}else {
									
									JOptionPane.showMessageDialog(null," Horario Invalido ") ;
								}
							}else {	
								
								
								if ( horaAbertura.compareTo(maxHora) < 0 && horaFechamento.compareTo(maxHora) < 0 ) {
		
									horario.setHoraAbertura(horaAbertura);
									horario.setHoraFechamento(horaFechamento);
									horario.setRestaurante(restauranteSelecionado);
									horario.setDiaDaSemana(diaSelecionado);
									horarioService.salvar(horario);
									isAlterar = false ;	
									nullCamp();
									atualizaTable(restauranteSelecionado);
									
								}else {
									
									JOptionPane.showMessageDialog(null," Horario Invalido ") ;
								}
							
							}
							
						
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					JOptionPane.showMessageDialog(contentPane,"erro ao adicionar os horarios. Motivo: " + e2.getMessage());
		
				}
				

			}
		});
		btnAdicionar.setBounds(468, 55, 108, 21);
		contentPane.add(btnAdicionar);

		JLabel lblNewLabel_4 = new JLabel("Ações");
		lblNewLabel_4.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(383, 159, 45, 13);
		contentPane.add(lblNewLabel_4);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					 
					int isLinhaSelecinada = tableHorarios.getSelectedRow();
				
					if (isLinhaSelecinada >= 0) {
						
						
						TableModelHorarios model = (TableModelHorarios) tableHorarios.getModel();
						horarioSelecionado = model.getPor(isLinhaSelecinada);
						isAlterar = true ;
						editaHorario(horarioSelecionado);
						tableHorarios.updateUI();

					} else {
						
						JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para edição");
					}

				} catch (Exception e2) {
				
					throw new IllegalArgumentException(
							"Ocorreu um erro ao selecionar a lina na tabela. Motivo: " + e2.getMessage());
				}

			}

		});
		btnEditar.setBounds(390, 180, 168, 21);
		contentPane.add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {

					int isLinhaSelecinada = tableHorarios.getSelectedRow();
		            TableModelHorarios model = (TableModelHorarios) tableHorarios.getModel();
		            Horario horarioSelecionado = model.getPor(isLinhaSelecinada);
					
		            if (isLinhaSelecinada >= 0) {
						
						   horarioService.removerPor(horarioSelecionado.getId());
                             
						   int confirmacao = JOptionPane.showConfirmDialog(contentPane, "Deseja excluir ?", "Confirmação de Exclusão .",
		                            JOptionPane.YES_NO_OPTION);
						   
						   if (confirmacao == 0 ) {

							   model.removerPorId(isLinhaSelecinada);
	                           tableHorarios.updateUI();
	                           JOptionPane.showMessageDialog(contentPane ,"cadastro excluido .");
	                           
						   }

					} else {
						
						JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para edição");
					}

				} catch (Exception e2) {
				
					throw new IllegalArgumentException(
							"Ocorreu um erro ao selecionar a lina na tabela. Motivo: " + e2.getMessage());
				}

				
				
			}
		});
		btnExcluir.setBounds(390, 221, 165, 21);
		contentPane.add(btnExcluir);

		JLabel lblNewLabel_5 = new JLabel("Horários");
		lblNewLabel_5.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(14, 110, 59, 13);
		contentPane.add(lblNewLabel_5);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 130, 326, 209);
		contentPane.add(scrollPane);

		tableHorarios = new JTable();
		scrollPane.setViewportView(tableHorarios);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(377, 259, 195, 8);
		contentPane.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 0, 1, 2);
		contentPane.add(separator_1);

		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(377, 165, 10, 94);
		contentPane.add(separator_2);

		JSeparator separator_2_1 = new JSeparator();
		separator_2_1.setOrientation(SwingConstants.VERTICAL);
		separator_2_1.setForeground(Color.BLACK);
		separator_2_1.setBounds(571, 165, 10, 94);
		contentPane.add(separator_2_1);

		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(425, 166, 147, 8);
		contentPane.add(separator_3);

		JSeparator separator_2_2 = new JSeparator();
		separator_2_2.setOrientation(SwingConstants.VERTICAL);
		separator_2_2.setForeground(Color.BLACK);
		separator_2_2.setBounds(7, 115, 10, 232);
		contentPane.add(separator_2_2);

		JSeparator separator_2_2_1 = new JSeparator();
		separator_2_2_1.setOrientation(SwingConstants.VERTICAL);
		separator_2_2_1.setForeground(Color.BLACK);
		separator_2_2_1.setBounds(349, 118, 10, 229);
		contentPane.add(separator_2_2_1);

		JSeparator separator_4 = new JSeparator();
		separator_4.setForeground(Color.BLACK);
		separator_4.setBounds(8, 346, 341, 8);
		contentPane.add(separator_4);

		JSeparator separator_5 = new JSeparator();
		separator_5.setForeground(Color.BLACK);
		separator_5.setBounds(68, 118, 281, 8);
		contentPane.add(separator_5);
		
		JLabel lblNewLabel_1_1 = new JLabel("Abertura");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1.setBounds(205, 59, 59, 13);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Fechamento");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setBounds(327, 59, 74, 13);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(502, 326, 74, 21);
		contentPane.add(btnCancelar);

		setLocationRelativeTo(null);
		this.restauranteService = new RestauranteService();
		this.horarioService = new HorarioService();
		this.carregarCombo();

	}
}
