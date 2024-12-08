package com.vinicius.lista_spring.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.vinicius.lista_spring.model.Produto;
import com.vinicius.lista_spring.repository.ProdutoRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Service
@Validated
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository){
        this.produtoRepository = produtoRepository;
    }

    // public List<Produto> listarTodos(){
    //     return this.produtoRepository.findAll();
    // }

    public Page<Produto> listarPaginados(@PositiveOrZero @NotNull int page, @Positive @NotNull int size, @NotNull String[] sort){
        Sort sorting = Sort.by(Sort.Direction.fromString(sort[1]), sort[0]); // Sort.by(direção, coluna)
        return this.produtoRepository.findAll(PageRequest.of(page, size, sorting));
    }

    public Map<String, Object> getRelatorio(){
        Long totalProdutos = this.produtoRepository.contarProdutos();
        BigDecimal somaPrecos = this.produtoRepository.somarPrecos();

        return Map.of(
            "totalProdutos", totalProdutos,
            "somaPrecos", somaPrecos
        );
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
    
    public Produto alterarPreco(@NotNull @Positive Long id, @Valid Map<String, BigDecimal> request){
        BigDecimal novoPreco = request.get("novoPreco");

        Produto p = this.produtoRepository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if(novoPreco != null && novoPreco.compareTo(BigDecimal.ZERO) > 0){
            p.setPreco(novoPreco);
        }else{
            throw new IllegalArgumentException("O novo preço deve ser maior que zero");
        }
        
        return this.produtoRepository.save(p);
    }
}
