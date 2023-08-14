package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Categoria;

public interface DaoCategoria {
	
	public void inserir(Categoria categoria);
	
	public void Alterar(Categoria categoria) ;
	
	public void ExcluirPor(int id);
	
	public Categoria buscarPor(int id);
	
	public List<Categoria> listarPor(String nome); 



}
