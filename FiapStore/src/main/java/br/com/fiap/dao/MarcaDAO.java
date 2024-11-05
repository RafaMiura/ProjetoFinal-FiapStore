package br.com.fiap.dao;

import java.util.List;

import br.com.fiap.model.Marca;

public interface MarcaDAO {

	public Marca listarPorId(int id);
	public List<Marca> listarTodas();
	
}
