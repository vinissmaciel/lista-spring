package com.vinicius.lista_spring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.vinicius.lista_spring.model.Produto;
import com.vinicius.lista_spring.repository.ProdutoRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarTodos(){
        return this.produtoRepository.findAll();
    }

    public Produto salvar(@Valid Produto produto){
        return this.produtoRepository.save(produto);
    }

    public boolean deletar(@NotNull @Positive Long id){
        return this.produtoRepository.findById(id)
            .map(recordFound -> {
                this.produtoRepository.deleteById(id);
                return true;
            }) 
            .orElse(false);
        }
}
