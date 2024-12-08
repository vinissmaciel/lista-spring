package com.vinicius.lista_spring.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vinicius.lista_spring.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

    List<Produto> findByNomeContainingIgnoreCase(String nome); 

    @Query("SELECT SUM(p.quantidade) FROM Produto p")
    Long contarProdutos();

    @Query("SELECT SUM(p.preco*p.quantidade) FROM Produto p")
    BigDecimal somarPrecos();
}
