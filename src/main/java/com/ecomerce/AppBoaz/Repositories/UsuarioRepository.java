package com.ecomerce.AppBoaz.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomerce.AppBoaz.Models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	public List<Usuario> findByNomeContaining(String nome);
	public Optional<Usuario> findByUsuario(String usuario);
}
