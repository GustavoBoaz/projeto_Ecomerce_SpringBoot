package com.ecomerce.AppBoaz.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.AppBoaz.Models.Produto;
import com.ecomerce.AppBoaz.Models.Util.Categoria;
import com.ecomerce.AppBoaz.Services.ProdutoServices;

@RestController
@CrossOrigin("*")
public class ProdutoController {
	
	private @Autowired ProdutoServices services;
	
	@GetMapping("/produtos")
	public ResponseEntity<List<Produto>> pegarPorNome(@RequestParam(defaultValue = "") String nomeProduto){
		return new ResponseEntity<List<Produto>>(services.pegarProdutosPorNome(nomeProduto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/produtos/categoria")
	public ResponseEntity<List<Produto>> pegarPorCategoria(@RequestParam(defaultValue = "") Categoria categoria){
		return new ResponseEntity<List<Produto>>(services.pegarProdutosPorCategoria(categoria), HttpStatus.ACCEPTED);
	}

}
