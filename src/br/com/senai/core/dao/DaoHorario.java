package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;

public interface DaoHorario {
	
	public void inserir(Horario horario);
	
	public void Alterar(Horario horario) ;
	
	public void ExcluirPor(int id);
	
	public Horario buscarPor(int id);
	
	public List<Horario> listarPor(Restaurante restaurante ); 
	
	public List<Horario> listarPorId(int id );
	
	public int contaPor(int idDoRestaurante);
	

}
