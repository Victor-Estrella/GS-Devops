package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.AbrigoRequest;
import br.com.fiap.safehub.dto.AbrigoResponse;
import br.com.fiap.safehub.exception.IdNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/abrigos")
@Tag(name = "Abrigos", description = "Operações relacionadas ao cadastro de abrigos")
public interface AbrigoApi {

    @Operation(summary = "Listar todos os abrigos", description = "Retorna uma lista com todos os abrigos cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de abrigos retornada com sucesso")
    })
    @GetMapping
    ResponseEntity<List<AbrigoResponse>> listAll();

    @Operation(summary = "Buscar abrigo por ID", description = "Retorna os dados de um abrigo específico com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Abrigo encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Abrigo não encontrado")
    })
    @GetMapping("/{idAbrigo}")
    ResponseEntity<AbrigoResponse> getById(@PathVariable Long idAbrigo) throws IdNotFoundException;



    @Operation(summary = "Cadastrar novo abrigo", description = "Cria um novo abrigo com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Abrigo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do abrigo")
    })
    @PostMapping
    ResponseEntity<AbrigoResponse> create(@Valid @RequestBody AbrigoRequest request);




    @Operation(summary = "Atualizar dados do abrigo", description = "Atualiza as informações de um abrigo existente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Abrigo atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Abrigo não encontrado")
    })
    @PutMapping("/{idAbrigo}")
    ResponseEntity<AbrigoResponse> update(
            @PathVariable Long idAbrigo,
            @Valid @RequestBody AbrigoRequest request);



    @Operation(summary = "Excluir abrigo", description = "Remove um abrigo existente com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Abrigo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Abrigo não encontrado")
    })
    @DeleteMapping("/{idAbrigo}")
    ResponseEntity<Void> delete(@PathVariable Long idAbrigo) throws IdNotFoundException;
}
