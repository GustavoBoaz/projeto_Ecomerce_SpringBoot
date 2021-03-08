package com.ecomerce.AppBoaz.Services;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecomerce.AppBoaz.Models.UsuarioLogin;
import com.ecomerce.AppBoaz.Models.Produto;
import com.ecomerce.AppBoaz.Models.Usuario;
import com.ecomerce.AppBoaz.Repositories.ProdutoRepository;
import com.ecomerce.AppBoaz.Repositories.UsuarioRepository;

@Service
public class UsuarioServices {

	private @Autowired UsuarioRepository repositoryUsuario;
	private @Autowired ProdutoRepository repositoryProduto;
	
	//	Novo Usuario
	public Usuario cadastrarUsuario(Usuario novoUsuario) {
		Optional<Usuario> usuarioExistente = repositoryUsuario.findByUsuario(novoUsuario.getUsuario());
		if(usuarioExistente.isPresent()) {
			return null;
		}
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String senhaCriptografada = encoder.encode(novoUsuario.getSenha());
		novoUsuario.setSenha(senhaCriptografada);
		return repositoryUsuario.save(novoUsuario);
	}
	
	// Logar Usuario
	public Optional<UsuarioLogin> logar(Optional<UsuarioLogin> usuarioLogin){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> usuarioPresente = repositoryUsuario.findByUsuario(usuarioLogin.get().getUsuario());

		if(usuarioPresente.isPresent()) {
			if(encoder.matches(usuarioLogin.get().getSenha(), usuarioPresente.get().getSenha())) {
				String auth = usuarioLogin.get().getUsuario() + ":" + usuarioLogin.get().getSenha();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				usuarioLogin.get().setToken(authHeader);				
				usuarioLogin.get().setNome(usuarioPresente.get().getNome());
				usuarioLogin.get().setSenha(usuarioPresente.get().getSenha());

				return usuarioLogin;
			}
		}
		return null;
	}
	
	// Cadastrar Produto
	public Produto cadastrarProduto(Produto novoProduto, Long idUsuario) {
		Produto produtoExistente = repositoryProduto.save(novoProduto);
		Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
		
		if(usuarioExistente.isPresent()) {
			produtoExistente.setCriadoPor(usuarioExistente.get());
			return repositoryProduto.save(produtoExistente);
		}
		return null;
	}
	
	// Efetuar Compra
	public Usuario comprarProduto(Long idUsuario, Long idProduto) {
		Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
		Optional<Produto> produtoExistente = repositoryProduto.findById(idProduto);
		
		if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
			usuarioExistente.get().getMinhasCompras().add(produtoExistente.get());
			return repositoryUsuario.save(usuarioExistente.get());
		}
		return null;
	}
	
	// Validar Lista de Compradores por Nome
	public List<Usuario> pegarUsuarios(String nome){
		return repositoryUsuario.findByNomeContaining(nome);
	}
	
	// Remover um produto
	public Usuario deletarProduto(Long idProduto, Long idUsuario) {
		Optional<Usuario> usuarioExistente = repositoryUsuario.findById(idUsuario);
		Optional<Produto> produtoExistente = repositoryProduto.findById(idProduto);
		
		if(usuarioExistente.isPresent() && produtoExistente.isPresent()) {
			produtoExistente.get().setCriadoPor(null);
			repositoryProduto.save(produtoExistente.get());
			repositoryProduto.deleteById(produtoExistente.get().getId());
			return repositoryUsuario.findById(usuarioExistente.get().getId()).get();
		}
		return null;
	}
}
