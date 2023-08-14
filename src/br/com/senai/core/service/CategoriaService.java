package br.com.senai.core.service;

import java.util.List;

import br.com.senai.core.dao.DaoCategoria;
import br.com.senai.core.dao.DaoRestaurante;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Categoria;

public class CategoriaService {

	private DaoCategoria dao;

	private DaoRestaurante daoRestaurante;

	public CategoriaService() {

		this.dao = FactoryDao.getInstance().getDaoCategoria();
		this.daoRestaurante = FactoryDao.getInstance().getDaoRestaurante();

	}

	public void salvar(Categoria categoria) {

		this.validar(categoria);
		boolean isJaInserido = categoria.getId() > 0;
		
		if (isJaInserido) {
			this.dao.Alterar(categoria);
		} else {
			this.dao.inserir(categoria);
			;
		}
	}

	public void removerPor(int id) {

		if (id > 0) {

			int qtdeRestaurante = daoRestaurante.contarPor(id);

			if (qtdeRestaurante > 0) {
				throw new IllegalArgumentException(
						"Não foi possível excluir a categoria . Motivo :" + qtdeRestaurante + "vinculados ");
			}

			this.dao.ExcluirPor(id);

		} else {
			throw new IllegalArgumentException("O id da categoria deve ser maior que zero ");
		}

	}

	public Categoria buscarPor(int id) {

		if (id > 0) {
			Categoria categoriaEncontrada = this.dao.buscarPor(id);
			if (categoriaEncontrada == null) {
				throw new IllegalArgumentException("Não foi encontrado categoria para o código informado");
			}
			return categoriaEncontrada;
		} else {
			throw new IllegalArgumentException("O id da categoria deve ser maior que 0");
		}
	}

	public List<Categoria> listarPor(String nome) {

		if (nome != null && nome.length() >= 3) {
			return this.dao.listarPor("%" + nome + "%");

		} else {

			throw new IllegalArgumentException("O filtro é obrigatório e deve conter mais de dois 2 caracteres");

		}
	}

	public List<Categoria> ListarTodas() {

		return dao.listarPor("%%");

	}

	private void validar(Categoria categoria) {

		if (categoria != null) {

			boolean isNomeInvalido = categoria.getNome() == null || categoria.getNome().isBlank()
					|| categoria.getNome().length() > 100 || categoria.getNome().length() < 3;

			if (isNomeInvalido) {
				throw new IllegalArgumentException(
						" o nome da categoria é obrigatorio e " + "não deve possuir mais de 100 caracters");
			}

		} else {
			throw new NullPointerException("A categoria não pode ser nula.");
		}

	}

}
