package br.com.fiap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.MarcaDAO;
import br.com.fiap.model.Marca;

public class OracleMarcaDAO implements MarcaDAO {
	private Connection conexao;

	@Override
	public Marca listarPorId(int id) {
		Marca marca = null;
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			String sql = "SELECT * FROM TB_MARCA WHERE ID_MARCA = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int codigo = rs.getInt("ID_MARCA");
				String nome = rs.getString("NOME_MARCA");
				String paisOrigem = rs.getString("PAIS_ORIGEM");
				String descricao = rs.getString("DESCRICAO");
				marca = new Marca(codigo, nome, paisOrigem, descricao);
			}
			rs.close();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return marca;
	}

	@Override
	public List<Marca> listarTodas() {
		List<Marca> lista = new ArrayList<Marca>();
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			String sql = "SELECT * FROM TB_MARCA";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("ID_MARCA");
				String nome = rs.getString("NOME_MARCA");
				String paisOrigem = rs.getString("PAIS_ORIGEM");
				String descricao = rs.getString("DESCRICAO");
				Marca marca = new Marca(codigo, nome, paisOrigem, descricao);
				lista.add(marca);
			}
			rs.close();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

}