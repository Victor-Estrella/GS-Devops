package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.EstoqueRequest;
import br.com.fiap.safehub.dto.EstoqueResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/estoques")
@Tag(name = "Estoque", description = "Operações de controle de estoque dos abrigos")
public interface EstoqueApi {

    @Operation(summary = "Listar todos os itens de estoque", description = "Retorna a lista completa dos itens de todos os estoques cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso")
    })
    @GetMapping("/abrigos/{idAbrigo}")
    ResponseEntity<EstoqueResponse> listAll(@PathVariable Long idAbrigo);



    @Operation(summary = "Buscar item de estoque por ID", description = "Retorna um item específico de estoque com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/{idEstoque}")
    ResponseEntity<EstoqueResponse> getById(@PathVariable Long idEstoque);



    @Operation(summary = "Cadastrar novo item no estoque", description = "Adiciona um novo item ao estoque.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item adicionado ao estoque com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do item")
    })
    @PostMapping("/abrigos/{idAbrigo}")
    ResponseEntity<EstoqueResponse> create(@PathVariable Long idAbrigo, @Valid @RequestBody EstoqueRequest request);



    @Operation(summary = "Atualizar item de estoque", description = "Atualiza os dados de um item existente no estoque.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PutMapping("/abrigos/{idAbrigo}")
    ResponseEntity<EstoqueResponse> updateEstoque(
        @PathVariable Long idAbrigo,
        @Valid @RequestBody EstoqueRequest request
    );


    @Operation(summary = "Excluir item de estoque", description = "Remove um item de estoque com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/{idEstoque}")
    ResponseEntity<Void> delete(@PathVariable Long idEstoque);

    @Operation(summary = "Excluir item de estoque", description = "Remove um item de estoque com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/abrigo/{idAbrigo}")
    ResponseEntity<Void> deleteIdAbrigo(@PathVariable Long idAbrigo);


    @Operation(summary = "Atualização parcial do estoque do abrigo", description = "Atualiza parcialmente os dados do estoque do abrigo.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estoque atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Abrigo não encontrado")
    })
    @PatchMapping("/abrigos/{idAbrigo}")
    ResponseEntity<EstoqueResponse> patchEstoque(
        @PathVariable Long idAbrigo,
        @RequestBody EstoqueRequest request
    );
}