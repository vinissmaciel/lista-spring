package com.vinicius.lista_spring.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.lista_spring.model.Produto;
import com.vinicius.lista_spring.service.ProdutoService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;




@Validated
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // @GetMapping
    // public @ResponseBody List<Produto> listarTodos(){
    //     return this.produtoService.listarTodos();
    // }

    @GetMapping
    public Page<Produto> listarPaginado(@RequestParam @PositiveOrZero @NotNull int page, @RequestParam @Positive @NotNull int size, @RequestParam @NotNull String[] sort) {
        return this.produtoService.listarPaginados(page, size, sort);
    }

    @GetMapping("/relatorio")
    public ResponseEntity<Map<String, Object>> getRelatorio() {
        Map<String, Object> relatorio = this.produtoService.getRelatorio();
        return ResponseEntity.ok(relatorio);
    }
    
    @PatchMapping("/{id}/preco")
    public ResponseEntity<Produto> alterarPreco(@NotNull @Positive @PathVariable Long id, @RequestBody @Valid Map<String, BigDecimal> request){
        return ResponseEntity.ok(this.produtoService.alterarPreco(id, request));
    } 

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Produto salvar(@RequestBody @Valid Produto produto){
        return this.produtoService.salvar(produto);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable @NotNull @Positive Long id){
        if(this.produtoService.deletar(id)){
            return ResponseEntity.noContent().<Void>build();
        }
        return ResponseEntity.notFound().build();  
    }
}
