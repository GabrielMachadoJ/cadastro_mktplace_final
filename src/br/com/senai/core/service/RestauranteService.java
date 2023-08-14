package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.DaoRestaurante;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;

public class RestauranteService {
	
	
	private DaoRestaurante dao ;
	private DaoHorario daoHorario;
	
	public RestauranteService() {

		this.dao = FactoryDao.getInstance().getDaoRestaurante();
		this.daoHorario = FactoryDao.getInstance().getDaoHorario();
	
	}

	public void salvar (Restaurante restaurante ) {
		
		this.validar(restaurante);
		boolean isJaInserido = restaurante.getId() > 0 ; 
		
		if(isJaInserido) {
			this.dao.alterar(restaurante);
		}else {
			this.dao.inserir(restaurante);
		

		}
	}

	public void ExcluirPor(int idRestaurante) {
		
		int qtdeDeHorarios = daoHorario.contaPor(idRestaurante);
		
		if(idRestaurante > 0 ) {
			boolean isExisteHorario = daoHorario.contaPor(idRestaurante) > 0 ;
			
			if (isExisteHorario) {
				throw new IllegalArgumentException("Não foi possivel excluir o restaurante . Motivo: Existem " + qtdeDeHorarios	
												  + "horários vinculados ao restaurante " );
			}
			
			this.dao.excluirPor(idRestaurante);
			
			
		}else {
			throw new IllegalArgumentException("O id da Restaurante deve ser maior que zero ! ");
		}
		
	}
	
	public List<Restaurante> listarTodos(){

		try {
			
			return this.dao.listarTodos();
			
			
		} catch (Exception e) {
			
			throw new NullPointerException("Não a restaurantes ! Motivo:" + e.getMessage() );
			
		}
		
	}
	
	public Restaurante buscarPor(int idRestaurante) {
		
		if (idRestaurante > 0) {
			
			Restaurante restauranteEncontrada = this.dao.buscarPor(idRestaurante);
			
			if (restauranteEncontrada == null) {
				throw new IllegalArgumentException("Não foi encontrado Restaurante para o código informado !");
			}
			return restauranteEncontrada;
			
		} else {
			throw new IllegalArgumentException("O id do Restaurante deve ser maior que 0 !");
		}
	}
	
	public List<Restaurante> listarPor(String nome , Categoria categoria) {
		
		boolean isCategoriaInformada = categoria != null && categoria.getId() > 0 ;
		
		boolean isNomeInformado = nome != null && !nome.isBlank();
		
		if(!isCategoriaInformada && !isNomeInformado) {
			throw new IllegalArgumentException( " Informe o nome e/ou categoria para listagem !");
		}
		
		String filtroNome = "" ;
		
		if (categoria == null && categoria.getId() > 0 ) {		
			filtroNome = nome + "% ";		
		}else {			
			filtroNome = "%" + nome + "%" ;		
		}
		
		return dao.listarPor(filtroNome , categoria ) ;
		
	}
	
	
	private void validar (Restaurante restaurante) {
		
		if (restaurante != null) {
			
			  boolean isCadastroInvalido = restaurante.getNome().isBlank()
									|| restaurante.getDescricao().isBlank()
									|| restaurante.getEndereco().getCidade().isBlank()
									|| restaurante.getEndereco().getLogradouro().isBlank()
									|| restaurante.getEndereco().getBairro().isBlank()
									|| restaurante.getCategoria() == null ;
			  
													
					if (isCadastroInvalido) {
						throw new IllegalArgumentException("Todos os campos são obrigatorios , Menos complemento . " );															
					}
			
			boolean isNomeInvalido = restaurante.getNome().length() > 250 ;
					
					if (isNomeInvalido) {
						throw new IllegalArgumentException("O campo nome só pode possuir 250 caracteres . " );															
					}
			boolean isCidadeInvalido = restaurante.getEndereco().getCidade().length() > 80 ;
					
					if (isCidadeInvalido) {
						throw new IllegalArgumentException("O campo Cidade só pode possuir 80 caracteres . " );															
					}
		    boolean isLogradouroInvalido = restaurante.getEndereco().getLogradouro().length() > 200 ;
					
					if (isLogradouroInvalido) {
						throw new IllegalArgumentException("O campo Logradouro só pode possuir 200 caracteres . " );															
					}
			boolean isBairroInvalido = restaurante.getEndereco().getBairro().length() > 250 ;
						
					if (isBairroInvalido) {
						throw new IllegalArgumentException("O campo Bairro só pode possuir 250 caracteres . " );															
					}			
					
			
		}else {
			throw new NullPointerException("O Restaurante não pode ter os campos nulos.");
		}
		
		
	}

}
