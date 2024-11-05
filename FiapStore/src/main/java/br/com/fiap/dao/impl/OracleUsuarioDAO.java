
package br.com.fiap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.model.Usuario;

public class OracleUsuarioDAO implements UsuarioDAO {

	private Connection conexao;

	@Override
	public boolean validarUsuario(Usuario usuario) {
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM TB_USUARIO WHERE EMAIL = ? AND SENHA = ?");
			stmt.setString(1, usuario.getEmail());
			stmt.setString(2, usuario.getSenha());
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			stmt.close();
			rs.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
