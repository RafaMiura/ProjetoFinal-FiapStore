package br.com.fiap.dao;

import java.util.List;

import br.com.fiap.exception.DBException;
import br.com.fiap.model.Produto;

public interface ProdutoDAO {

	public void cadastrar(Produto produto) throws DBException;
	public void atualizar(Produto produto) throws DBException;
	public void remover(int codigo) throws DBException;
	public Produto listarPorId(int id);
	public List<Produto> listarTodos();

}
