package br.com.fiap.dao;

import java.util.List;

import br.com.fiap.model.Categoria;
import br.com.fiap.model.Marca;

public interface CategoriaDAO {

	public Categoria listarPorId(int id);
	List<Categoria> listarTodas();

}
