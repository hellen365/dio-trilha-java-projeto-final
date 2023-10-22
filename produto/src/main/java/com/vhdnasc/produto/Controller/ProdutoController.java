package com.vhdnasc.produto.Controller;

import com.vhdnasc.produto.domain.dto.produto.ProdutoRequest;
import com.vhdnasc.produto.domain.dto.produto.ProdutoResponse;
import com.vhdnasc.produto.domain.impl.ProdutoUseCaseImpl;
import com.vhdnasc.produto.domain.mapper.ProdutorMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/users")
@Tag(name = "Produtos Controller", description = "API RESTful para gerenciamento de produtos.")
public class ProdutoController {

    private final ProdutoUseCaseImpl produtoUseCase;

    private final ProdutorMapper mapper;

    public ProdutoController(ProdutoUseCaseImpl produtoUseCase, ProdutorMapper mapper) {
        this.produtoUseCase = produtoUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Obter todos os produtos", description = "Recuperar uma lista de todos os produtos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida")
    })
    public ResponseEntity<List<ProdutoResponse>> findAll() {
        var response = mapper.toListResponse(produtoUseCase.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenha um produto por ID", description = "Recuperar um produto específico com base em seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoResponse> findById(@PathVariable Long id) {
        var user = mapper.toResponse(produtoUseCase.findById(id));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Crie um novo produto", description = "Crie um novo produto e retorne os dados do produto criado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados do produto inválidos fornecidos")
    })
    public ResponseEntity<ProdutoResponse> create(@RequestBody ProdutoRequest request) {
        var user = produtoUseCase.create(mapper.toEntity(request));
        var response = mapper.toResponse(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um produto", description = "Atualizar os dados de um produto existente com base em seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucess"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "422", description = "Dados do produto inválidos fornecidos")
    })
    public ResponseEntity<ProdutoResponse> update(@PathVariable Long id, @RequestBody ProdutoRequest request) {
        var user = produtoUseCase.update(id, mapper.toEntity(request));
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um produto", description = "Exclua um produto existente com base em seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
