package com.vinicius.lista_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vinicius.lista_spring.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findByNomeContainingIgnoreCase(String nome); 
}
