package com.ecomerce.AppBoaz.Controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecomerce.AppBoaz.Models.Produto;
import com.ecomerce.AppBoaz.Models.Usuario;
import com.ecomerce.AppBoaz.Models.UsuarioLogin;
import com.ecomerce.AppBoaz.Services.UsuarioServices;

@RestController
@CrossOrigin("*")
public class UsuarioController {
	
	private @Autowired UsuarioServices services;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Usuario> cadastro(@Valid @RequestBody Usuario novoUsuario){
		return new ResponseEntity<Usuario>(services.cadastrarUsuario(novoUsuario), HttpStatus.CREATED);
	}
	
	@PostMapping("/logar")
	public ResponseEntity<UsuarioLogin> auth(@RequestBody Optional<UsuarioLogin> usuarioLogin){
		return services.logar(usuarioLogin)
				.map(usuario -> ResponseEntity.ok(usuario))
				.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
	
	@PostMapping("/produto/novo/{id_Usuario}")
	public ResponseEntity<?> novoProduto(
			@PathVariable(value = "id_Usuario") Long idUsuario,
			@Valid @RequestBody Produto novoProduto){
		Produto cadastro =  services.cadastrarProduto(novoProduto, idUsuario);
		if(cadastro == null) {
			return new ResponseEntity<String>("Falha no cadastro", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Produto>(cadastro, HttpStatus.CREATED);
	}
	
	@PutMapping("/produto/compra/{id_Produto}/{id_Usuario}")
	public ResponseEntity<?> novaCompra(
			@PathVariable(value = "id_Produto") Long idProduto,
			@PathVariable(value = "id_Usuario") Long idUsuario){
		Usuario compra = services.comprarProduto(idUsuario, idProduto);
		if(compra == null) {
			return new ResponseEntity<String>("Produto ou Usuario invalido", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Usuario>(compra, HttpStatus.CREATED);
	}
	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> pegarUsuario(@RequestParam(defaultValue = "") String nome){
		return new ResponseEntity<List<Usuario>>(services.pegarUsuarios(nome), HttpStatus.OK);
	}
	
	@DeleteMapping("/produto/delete/{id_Produto}/{id_Usuario}")
	public ResponseEntity<?> removerProduto(
			@PathVariable(value = "id_Produto") Long idProduto,
			@PathVariable(value = "id_Usuario") Long idUsuario){
		Usuario retorno = services.deletarProduto(idProduto, idUsuario);
		if(retorno == null) {
			return new ResponseEntity<String>("Produto ou Usuario invalido", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Usuario>(retorno, HttpStatus.ACCEPTED);
	}
}
