package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.UsuarioRequest;
import br.com.fiap.safehub.dto.UsuarioResponse;
import br.com.fiap.safehub.exception.IdNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Operações de gerenciamento de usuários do sistema")
public interface UsuarioApi {

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso")
    })
    @GetMapping
    ResponseEntity<List<UsuarioResponse>> listAll();

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário específico com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping("/{idUsuario}")
    ResponseEntity<UsuarioResponse> getById(@PathVariable Long idUsuario) throws IdNotFoundException;

    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo usuário com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do usuário")
    })
    @PostMapping
    ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request);

    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza as informações de um usuário existente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PutMapping("/{idUsuario}")
    ResponseEntity<UsuarioResponse> update(
            @PathVariable Long idUsuario,
            @Valid @RequestBody UsuarioRequest request);

    @Operation(summary = "Excluir usuário", description = "Remove um usuário existente com base no ID informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping("/{idUsuario}")
    ResponseEntity<Void> delete(@PathVariable Long idUsuario) throws IdNotFoundException;

}