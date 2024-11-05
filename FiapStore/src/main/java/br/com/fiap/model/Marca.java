package br.com.fiap.model;

public class Marca {

	private String nome, paisOrigem, descricao;
	private int id;

	public Marca() {
	}

	public Marca(int id, String nome, String paisOrigem, String descricao) {
		this.id = id;
		this.nome = nome;
		this.paisOrigem = paisOrigem;
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
