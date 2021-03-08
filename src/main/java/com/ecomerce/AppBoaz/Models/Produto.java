package com.ecomerce.AppBoaz.Models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ecomerce.AppBoaz.Models.Util.Categoria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Produto {
	
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private @NotNull @Size(min = 2, max = 20, message = "Error size name") String nome;
	private @NotNull @Size(min = 2, max = 20, message = "Error size value") String preco;
	private @NotNull @Size(min = 2, max = 99, message = "Error size description")String descricao;
	private @Enumerated(EnumType.STRING) Categoria categoria;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "criador")
	@JsonIgnoreProperties({"meusProdutos", "minhasCompras", "senha"})
	private Usuario criadoPor;
	
	@ManyToMany(mappedBy = "minhasCompras", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties({"senha", "meusProdutos", "minhasCompras"})
	private List<Usuario> compradoPor = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Usuario getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(Usuario criadoPor) {
		this.criadoPor = criadoPor;
	}

	public List<Usuario> getCompradoPor() {
		return compradoPor;
	}

	public void setCompradoPor(List<Usuario> compradoPor) {
		this.compradoPor = compradoPor;
	}
}
