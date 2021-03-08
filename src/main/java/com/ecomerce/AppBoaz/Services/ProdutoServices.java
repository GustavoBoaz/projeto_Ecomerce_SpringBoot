package com.ecomerce.AppBoaz.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomerce.AppBoaz.Models.Produto;
import com.ecomerce.AppBoaz.Models.Util.Categoria;
import com.ecomerce.AppBoaz.Repositories.ProdutoRepository;

@Service
public class ProdutoServices {

	private @Autowired ProdutoRepository repositoryProduto;
	
	// Validar Lista de Produtos por Nome
	public List<Produto> pegarProdutosPorNome(String nome){
		return repositoryProduto.findByNomeContaining(nome);
	}
	
	// Validar Lista de Produtos por Categoria
	public List<Produto> pegarProdutosPorCategoria(Categoria categoria){
		return repositoryProduto.findByCategoria(categoria);
	}
}
