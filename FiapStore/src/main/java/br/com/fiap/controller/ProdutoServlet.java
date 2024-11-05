package br.com.fiap.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.fiap.dao.CategoriaDAO;
import br.com.fiap.dao.MarcaDAO;
import br.com.fiap.dao.ProdutoDAO;
import br.com.fiap.exception.DBException;
import br.com.fiap.factory.DAOFactory;
import br.com.fiap.model.Categoria;
import br.com.fiap.model.Marca;
import br.com.fiap.model.Produto;

@WebServlet("/produto")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProdutoDAO dao;
	private CategoriaDAO categoriaDao;
	private MarcaDAO marcaDao;

	@Override
	public void init() throws ServletException {
		super.init();
		dao = DAOFactory.getProdutoDAO();
		categoriaDao = DAOFactory.getCategoriaDAO();
		marcaDao = DAOFactory.getMarcaDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		switch (acao) {
		case "cadastrar":
			cadastrar(request, response);
			break;
		case "editar":
			editar(request, response);
			break;
		case "excluir":
			excluir(request, response);
			break;
		default:
			break;
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		switch (acao) {
		case "listar":
			listar(request, response);
			break;
		case "abrir-form-cadastro":
			abrirFormCadastro(request, response);
			break;
		case "abrir-form-edicao":
			abrirFormEdicao(request, response);
			break;
		}
	}

	private void abrirFormCadastro(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		carregarOpcoesCategoria(request);
		carregarOpcoesMarca(request);
		request.getRequestDispatcher("cadastro-produto.jsp").forward(request, response);
	}

	private void carregarOpcoesCategoria(HttpServletRequest request) {
		List<Categoria> lista = categoriaDao.listarTodas();
		request.setAttribute("categorias", lista);
	}

	private void carregarOpcoesMarca(HttpServletRequest request) {
		List<Marca> lista = marcaDao.listarTodas();
		request.setAttribute("marcas", lista);
	}

	private void abrirFormEdicao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("codigo"));
		Produto produto = dao.listarPorId(id);
		request.setAttribute("produto", produto);
		carregarOpcoesCategoria(request);
		carregarOpcoesMarca(request);
		request.getRequestDispatcher("edicao-produto.jsp").forward(request, response);
	}

	private void listar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Produto> lista = dao.listarTodos();
		request.setAttribute("produtos", lista);
		request.getRequestDispatcher("lista-produto.jsp").forward(request, response);
	}

	private void cadastrar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String nome = request.getParameter("nome");
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			double preco = Double.parseDouble(request.getParameter("valor"));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fabricacao = Calendar.getInstance();
			fabricacao.setTime(format.parse(request.getParameter("fabricacao")));
			int codigoCategoria = Integer.parseInt(request.getParameter("categoria"));
			int idMarca = Integer.parseInt(request.getParameter("marca"));
			Categoria categoria = new Categoria();
			categoria.setCodigo(codigoCategoria);
			Marca marca = new Marca();
			marca.setId(idMarca);
			Produto produto = new Produto(nome, preco, fabricacao, quantidade);
			produto.setCategoria(categoria);
			produto.setMarca(marca);
			dao.cadastrar(produto);
			request.setAttribute("msg", "Produto cadastrado!");
		} catch (DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao cadastrar");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Por favor, valide os dados");
		}
		abrirFormCadastro(request, response);
	}

	private void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int codigo = Integer.parseInt(request.getParameter("codigo"));
			String nome = request.getParameter("nome");
			int quantidade = Integer.parseInt(request.getParameter("quantidade"));
			double preco = Double.parseDouble(request.getParameter("valor"));
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar fabricacao = Calendar.getInstance();
			fabricacao.setTime(format.parse(request.getParameter("fabricacao")));
			int codigoCategoria = Integer.parseInt(request.getParameter("categoria"));
			int codigoMarca = Integer.parseInt(request.getParameter("marca"));
			Categoria categoria = categoriaDao.listarPorId(codigoCategoria);
			Marca marca = marcaDao.listarPorId(codigoMarca);
			Produto produto = new Produto(nome, preco, fabricacao, quantidade);
			produto.setCodigo(codigo);
			produto.setCategoria(categoria);
			produto.setMarca(marca);
			dao.atualizar(produto);
			request.setAttribute("msg", "Produto atualizado!");
		} catch (DBException db) {
			db.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", "Por favor, valide os dados");
		}
		listar(request, response);
	}

	private void excluir(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		try {
			dao.remover(codigo);
			request.setAttribute("msg", "Produto removido!");
		} catch (DBException e) {
			e.printStackTrace();
			request.setAttribute("erro", "Erro ao atualizar");
		}
		listar(request, response);
	}

}
