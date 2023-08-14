package br.com.senai.core.service;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;

public class HorarioService {
	
	private DaoHorario dao ;
	
	public HorarioService() {

		this.dao = FactoryDao.getInstance().getDaoHorario();
	}
	
	public void salvar (Horario horario ) {
		
		this.validar(horario);
			
		boolean isJaInserido = horario.getId() > 0 ; 
		
		if(isJaInserido) {
		
			this.dao.Alterar(horario);
			JOptionPane.showMessageDialog(null , "Horario alterado com sucesso!");
			
		}else {
			
			this.dao.inserir(horario);
			JOptionPane.showMessageDialog(null , "Horario salvo com sucesso!");

		}
	}
	
	public void removerPor (int id) {
	
		if(id > 0 ) {
			
			this.dao.ExcluirPor(id);

		}else {
			throw new IllegalArgumentException("O id da Horario deve ser maior que zero .");
		}
		
	}
	
	public Horario buscarPor(int id) {
		
		if (id > 0) {
			Horario HorarioEncontrado = this.dao.buscarPor(id);
			if (HorarioEncontrado == null) {
				throw new IllegalArgumentException("Não foi encontrado Horario para o código informado .");
			}
			return HorarioEncontrado;
		} else {
			throw new IllegalArgumentException("O id da Horario deve ser maior que 0 .");
		}
	}
	
	public List<Horario> listarPor(Restaurante restaurante) {
		
		if (restaurante != null ) {
			return this.dao.listarPor(restaurante);
		}
		
		throw new IllegalArgumentException("O restaurante é obrigatório .");
	}
	public List<Horario> listarPorId (int id) {
		
		if ( id > 0 ) {
			return this.dao.listarPorId(id);
		}
		
		throw new IllegalArgumentException("O id é obrigatório .");
	}
	
	private void validar (Horario horario) {
		
		List<Horario> horarios = new ArrayList<Horario>();
		
		horarios.addAll(listarPorId(horario.getRestaurante().getId()));
		
		boolean isPriEdicao = (horarios.size() <= 1 );
		
		if (isPriEdicao ) {
			
			isPrimeiraVal(horario);
			
		}
		
		horarios.remove(horario);
	
	 	for (Horario h : horarios) {
	 			
			boolean isCampoNull  = horario.getDiaDaSemana().isBlank()
								|| horario.getHoraAbertura() == null 
								|| horario.getHoraFechamento() == null;
			
			
			if (isCampoNull) {
				throw new IllegalArgumentException("Campo nulo obrigatorio !");
			}
				
			boolean isHorarioExistente = (horario.getHoraAbertura().compareTo(h.getHoraAbertura()) >= 0 && 
							             horario.getHoraAbertura().compareTo(h.getHoraFechamento()) < 0) ||
							             (horario.getHoraFechamento().compareTo(h.getHoraAbertura()) > 0 && 
							             horario.getHoraFechamento().compareTo(h.getHoraFechamento()) <= 0)
							             ||horario.getHoraAbertura().equals(h.getHoraAbertura())
							             ||horario.getHoraFechamento().equals( h.getHoraFechamento())
							             ;
		
			if(isHorarioExistente && horario.getDiaDaSemana().equals(h.getDiaDaSemana()) ) {
				throw new IllegalArgumentException("Horario ja existente.");
			}
			
			boolean isHorariosIguais = (horario.getHoraAbertura().equals(horario.getHoraFechamento()));
			
			if(isHorariosIguais) {
				throw new IllegalArgumentException("Horario de abertura não pode ser igual o de fechamento.");
			}
			
			boolean isHorariosSuperior = (horario.getHoraFechamento().compareTo(horario.getHoraAbertura()) < 0 );
			
			if(isHorariosSuperior) {
				throw new IllegalArgumentException("Horario de abertura não pode ser maior que o de fechamento.");
			}									
			
		}
	
	}
	
	private void isPrimeiraVal(Horario horario) {
				
		boolean isCampoNull  = horario.getDiaDaSemana().isBlank()
							|| horario.getHoraAbertura() == null 
							|| horario.getHoraFechamento() == null;
		
		
		if (isCampoNull) {
		throw new IllegalArgumentException("Campo nulo obrigatorio !");
		}
		
		boolean isHorariosIguais = (horario.getHoraAbertura().equals(horario.getHoraFechamento()));
		
		if(isHorariosIguais) {
		throw new IllegalArgumentException("Horario de abertura não pode ser igual o de fechamento.");
		}
		
		boolean isHorariosSuperior = (horario.getHoraFechamento().compareTo(horario.getHoraAbertura()) < 0 );
		
		if(isHorariosSuperior) {
		throw new IllegalArgumentException("Horario de abertura não pode ser maior que o de fechamento.");
		}									

		
	}
	
	
}
