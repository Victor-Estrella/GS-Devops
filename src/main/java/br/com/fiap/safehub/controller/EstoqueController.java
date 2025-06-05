package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.EstoqueRequest;
import br.com.fiap.safehub.dto.EstoqueResponse;
import br.com.fiap.safehub.dto.ItemEstoqueDTO;
import br.com.fiap.safehub.entity.EstoqueAbrigo;
import br.com.fiap.safehub.service.CadastroUsuarioService;
import br.com.fiap.safehub.service.EstoqueAbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EstoqueController implements EstoqueApi {

    @Autowired
    private EstoqueAbrigoService estoqueService;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Override
    public ResponseEntity<EstoqueResponse> listAll(@PathVariable Long idAbrigo) {
        EstoqueResponse response = estoqueService.buscarAgrupadoPorAbrigo(idAbrigo);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<EstoqueResponse> getById(@PathVariable Long idEstoque) {
        return estoqueService.findById(idEstoque)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Override
    public ResponseEntity<EstoqueResponse> create(@PathVariable Long idAbrigo, @Valid @RequestBody EstoqueRequest request) {
        estoqueService.create(idAbrigo, request);
        EstoqueResponse response = estoqueService.buscarAgrupadoPorAbrigo(idAbrigo);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable Long idEstoque) {
        estoqueService.deletar(idEstoque);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteIdAbrigo(Long idAbrigo) {
        estoqueService.deleteIdAbrigo(idAbrigo);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<EstoqueResponse> updateEstoque(
            @PathVariable Long idAbrigo,
            @Valid @RequestBody EstoqueRequest request) {
        estoqueService.updateEstoque(idAbrigo, request);
        EstoqueResponse response = estoqueService.buscarAgrupadoPorAbrigo(idAbrigo);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<EstoqueResponse> patchEstoque(
            @PathVariable Long idAbrigo,
            @RequestBody EstoqueRequest request) {
        estoqueService.patchEstoque(idAbrigo, request);
        EstoqueResponse response = estoqueService.buscarAgrupadoPorAbrigo(idAbrigo);
        return ResponseEntity.ok(response);
    }

    private EstoqueResponse toResponse(EstoqueAbrigo item) {
        return EstoqueResponse.builder()
                .alimentos(item.getAlimento() != null ?
                        List.of(ItemEstoqueDTO.builder()
                                .idEstoque(item.getIdEstoque())
                                .nome(item.getAlimento())
                                .quantidade(item.getQuantidadeAlimento())
                                .build()) :
                        List.of())
                .roupas(item.getRoupa() != null ?
                        List.of(ItemEstoqueDTO.builder()
                                .idEstoque(item.getIdEstoque())
                                .nome(item.getRoupa())
                                .quantidade(item.getQuantidadeRoupa())
                                .build()) :
                        List.of())
                .medicamentos(item.getMedicamento() != null ?
                        List.of(ItemEstoqueDTO.builder()
                                .idEstoque(item.getIdEstoque())
                                .nome(item.getMedicamento())
                                .quantidade(item.getQuantidadeMedicamento())
                                .build()) :
                        List.of())
                .litrosAgua(item.getLitrosAgua())
                .numeroPessoa(item.getNumeroPessoa())
                .chaveAbrigo(item.getChaveAbrigo().getIdCadastroAbrigo())
                .build();
    }
}
