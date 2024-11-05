package br.com.fiap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.CategoriaDAO;
import br.com.fiap.model.Categoria;
import br.com.fiap.model.Marca;

public class OracleCategoriaDAO implements CategoriaDAO {

	private Connection conexao;

	@Override
	public List<Categoria> listarTodas() {
		List<Categoria> lista = new ArrayList<Categoria>();
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM TB_CATEGORIA");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("ID_CATEGORIA");
				String nome = rs.getString("NOME_CATEGORIA");
				Categoria categoria = new Categoria(codigo, nome);
				lista.add(categoria);
			}
			rs.close();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public Categoria listarPorId(int id) {
		Categoria categoria = null;
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			String sql = "SELECT * FROM TB_CATEGORIA WHERE ID_CATEGORIA = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int codigo = rs.getInt("ID_CATEGORIA");
				String nome = rs.getString("NOME_CATEGORIA");
				categoria = new Categoria(codigo, nome);
			}
			rs.close();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoria;
	}
}
