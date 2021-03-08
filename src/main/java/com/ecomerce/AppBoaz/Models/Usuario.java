package com.ecomerce.AppBoaz.Models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Usuario {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private @NotNull @Size(min = 2, max = 20, message = "Error size name") String nome;
	private @NotNull @Size(min = 2, max = 20, message = "Error size user") String usuario;
	private @NotNull @Size(min = 2, message = "Error size pass") String senha;
	
	@OneToMany(mappedBy = "criadoPor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnoreProperties("criadoPor")
	private List<Produto> meusProdutos = new ArrayList<>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(
	  name = "compras", 
	  joinColumns = @JoinColumn(name = "comprador_id"), 
	  inverseJoinColumns = @JoinColumn(name = "produto_id"))
	@JsonIgnoreProperties("compradoPor")
	private List<Produto> minhasCompras = new ArrayList<>();

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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Produto> getMeusProdutos() {
		return meusProdutos;
	}

	public void setMeusProdutos(List<Produto> meusProdutos) {
		this.meusProdutos = meusProdutos;
	}

	public List<Produto> getMinhasCompras() {
		return minhasCompras;
	}

	public void setMinhasCompras(List<Produto> minhasCompras) {
		this.minhasCompras = minhasCompras;
	}
	
}
