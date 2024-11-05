package br.com.fiap.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.connection.ConnectionFactory;
import br.com.fiap.dao.ProdutoDAO;
import br.com.fiap.exception.DBException;
import br.com.fiap.model.Categoria;
import br.com.fiap.model.Marca;
import br.com.fiap.model.Produto;

public class OracleProdutoDAO implements ProdutoDAO {

	private Connection conexao;

	@Override
	public void cadastrar(Produto produto) throws DBException {
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			String sql = "INSERT INTO TB_PRODUTO (NOME_PRODUTO, QUANTIDADE, VALOR, DT_FABRICACAO, ID_CATEGORIA, ID_MARCA) VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());
			java.sql.Date data = new java.sql.Date(produto.getDataFabricacao().getTimeInMillis());
			stmt.setDate(4, data);
			stmt.setInt(5, produto.getCategoria().getCodigo());
			stmt.setInt(6, produto.getMarca().getId());
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Erro ao cadastrar.");
		}
	}

	@Override
	public void atualizar(Produto produto) throws DBException {
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			String sql = "UPDATE TB_PRODUTO SET NOME_PRODUTO = ?, QUANTIDADE = ?, VALOR = ?, DT_FABRICACAO = ?, ID_CATEGORIA = ?, ID_MARCA = ? WHERE ID_PRODUTO = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setInt(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());
			java.sql.Date data = new java.sql.Date(produto.getDataFabricacao().getTimeInMillis());
			stmt.setDate(4, data);
			stmt.setInt(5, produto.getCategoria().getCodigo());
			stmt.setInt(6, produto.getMarca().getId());
			stmt.setInt(7, produto.getCodigo());
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Erro ao atualizar.");
		}
	}

	@Override
	public Produto listarPorId(int id) {
		Produto produto = null;
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM TB_PRODUTO "
					+ "INNER JOIN TB_CATEGORIA ON TB_PRODUTO.ID_CATEGORIA = TB_CATEGORIA.ID_CATEGORIA "
					+ "INNER JOIN TB_MARCA ON TB_PRODUTO.ID_MARCA = TB_MARCA.ID_MARCA "
					+ "WHERE TB_PRODUTO.ID_PRODUTO = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// Extrai dados do produto
				int codigo = rs.getInt("ID_PRODUTO");
				String nome = rs.getString("NOME_PRODUTO");
				int qtd = rs.getInt("QUANTIDADE");
				double valor = rs.getDouble("VALOR");
				java.sql.Date data = rs.getDate("DT_FABRICACAO");
				Calendar dataFabricacao = Calendar.getInstance();
				dataFabricacao.setTimeInMillis(data.getTime());

				// Extrai dados da categoria
				int codigoCategoria = rs.getInt("ID_CATEGORIA");
				String nomeCategoria = rs.getString("NOME_CATEGORIA");

				// Extrai dados da marca e inicializa o objeto Marca
				int idMarca = rs.getInt("ID_MARCA");
				String nomeMarca = rs.getString("NOME_MARCA");
				String paisOrigemMarca = rs.getString("PAIS_ORIGEM");
				String descricaoMarca = rs.getString("DESCRICAO");

				Marca marca = new Marca(idMarca, nomeMarca, paisOrigemMarca, descricaoMarca);
				Categoria categoria = new Categoria(codigoCategoria, nomeCategoria);
				// Inicializa o produto com os dados extraídos
				produto = new Produto(nome, valor, dataFabricacao, qtd);
				produto.setCategoria(categoria);
				produto.setMarca(marca);
				produto.setCodigo(codigo);
			}
			rs.close();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produto;
	}

	@Override
	public List<Produto> listarTodos() {
		List<Produto> lista = new ArrayList<>();
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			PreparedStatement stmt = conexao
					.prepareStatement("SELECT p.id_produto, p.nome_produto, p.valor, p.dt_fabricacao, p.quantidade, "
							+ "c.id_categoria, c.nome_categoria, "
							+ "m.id_marca, m.nome_marca, m.pais_origem, m.descricao " + "FROM tb_produto p "
							+ "INNER JOIN tb_categoria c ON p.id_categoria = c.id_categoria "
							+ "INNER JOIN tb_marca m ON p.id_marca = m.id_marca");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				// Extrai dados do produto
				int idProduto = rs.getInt("ID_PRODUTO");
				String nomeProduto = rs.getString("NOME_PRODUTO");
				double valor = rs.getDouble("VALOR");
				java.sql.Date data = rs.getDate("DT_FABRICACAO");
				Calendar dataFabricacao = Calendar.getInstance();
				dataFabricacao.setTimeInMillis(data.getTime());
				int quantidade = rs.getInt("QUANTIDADE");

				// Extrai dados da categoria
				int idCategoria = rs.getInt("ID_CATEGORIA");
				String nomeCategoria = rs.getString("NOME_CATEGORIA");

				// Extrai dados da marca
				int idMarca = rs.getInt("ID_MARCA");
				String nomeMarca = rs.getString("NOME_MARCA");
				String paisOrigemMarca = rs.getString("PAIS_ORIGEM");
				String descricaoMarca = rs.getString("DESCRICAO");

				// Inicializa objetos Marca e Categoria
				Marca marca = new Marca(idMarca, nomeMarca, paisOrigemMarca, descricaoMarca);
				Categoria categoria = new Categoria(idCategoria, nomeCategoria);

				// Inicializa o produto com os dados extraídos
				Produto produto = new Produto(nomeProduto, valor, dataFabricacao, quantidade);
				produto.setCodigo(idProduto);
				produto.setCategoria(categoria);
				produto.setMarca(marca);

				// Adiciona o produto à lista
				lista.add(produto);
			}

			// Fechamento manual dos recursos
			rs.close();
			stmt.close();
			conexao.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public void remover(int codigo) throws DBException {
		try {
			conexao = ConnectionFactory.getInstance().getConnection();
			String sql = "DELETE FROM TB_PRODUTO WHERE ID_PRODUTO = ?";
			PreparedStatement stmt = conexao.prepareStatement(sql);
			stmt.setInt(1, codigo);
			stmt.execute();
			stmt.close();
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DBException("Erro ao remover.");
		}
	}

}
