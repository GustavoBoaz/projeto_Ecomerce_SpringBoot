package com.ecomerce.AppBoaz.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomerce.AppBoaz.Models.Produto;
import com.ecomerce.AppBoaz.Models.Util.Categoria;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public List<Produto> findByNomeContaining(String nome);
	public List<Produto> findByCategoria(Categoria categoria);
}
