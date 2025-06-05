package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.AbrigoRequest;
import br.com.fiap.safehub.dto.AbrigoResponse;
import br.com.fiap.safehub.entity.CadastroAbrigo;
import br.com.fiap.safehub.exception.IdNotFoundException;
import br.com.fiap.safehub.service.CadastroAbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AbrigoController implements AbrigoApi {

    @Autowired
    private CadastroAbrigoService abrigoService;

    @Override
    public ResponseEntity<List<AbrigoResponse>> listAll() {
        List<CadastroAbrigo> abrigos = abrigoService.findAll();

        List<AbrigoResponse> response = abrigos.stream()
                .map(abrigo -> AbrigoResponse.builder()

                        .idCadastroAbrigo(abrigo.getIdCadastroAbrigo())
                        .nomeAbrigo(abrigo.getNomeAbrigo())
                        .nomeResponsavel(abrigo.getNomeResponsavel())
                        .capacidadePessoa(abrigo.getCapacidadePessoa())
                        .localizacao(abrigo.getLocalizacao())

                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<AbrigoResponse> getById(@PathVariable Long idAbrigo) throws IdNotFoundException {

        /*Como funciona o Optional
        * Só que essa pessoa nem sempre tem o presente.
        * Então, ela te entrega uma caixa. Dentro dela
        * pode ou não estar a xícara.
        * */
        Optional<CadastroAbrigo> abrigoId = abrigoService.findById(idAbrigo);

        if (abrigoId.isPresent()) { // se o abrigo estiver presente no abrigoId
            CadastroAbrigo abrigo = abrigoId.get(); //vamos pegar esse id nesse atributo

            AbrigoResponse response = AbrigoResponse.builder()

                    .idCadastroAbrigo(abrigo.getIdCadastroAbrigo())
                    .nomeAbrigo(abrigo.getNomeAbrigo())
                    .nomeResponsavel(abrigo.getNomeResponsavel())
                    .capacidadePessoa(abrigo.getCapacidadePessoa())
                    .localizacao(abrigo.getLocalizacao())

                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build(); // caso não esteja presente devolve isso
        }

    }

    @Override
    public ResponseEntity<AbrigoResponse> create(@Valid @RequestBody AbrigoRequest request) {
        CadastroAbrigo abrigo = CadastroAbrigo.builder()
                .nomeAbrigo(request.getNomeAbrigo())
                .nomeResponsavel(request.getNomeResponsavel())
                .capacidadePessoa(request.getCapacidadePessoa())
                .localizacao(request.getLocalizacao())
                .build();

        CadastroAbrigo savedAbrigo = abrigoService.save(abrigo);

        AbrigoResponse response = AbrigoResponse.builder()
                .idCadastroAbrigo(savedAbrigo.getIdCadastroAbrigo())
                .nomeAbrigo(savedAbrigo.getNomeAbrigo())
                .nomeResponsavel(savedAbrigo.getNomeResponsavel())
                .capacidadePessoa(savedAbrigo.getCapacidadePessoa())
                .localizacao(savedAbrigo.getLocalizacao())
                .build();

        return ResponseEntity.ok(response);


    }

    @Override
    public ResponseEntity<AbrigoResponse> update(@PathVariable Long idAbrigo,
                                                 @Valid @RequestBody AbrigoRequest request) {

        return abrigoService.findById(idAbrigo)
                .map(existingAbrigo -> {

                    existingAbrigo.setNomeAbrigo(request.getNomeAbrigo());
                    existingAbrigo.setNomeResponsavel(request.getNomeResponsavel());
                    existingAbrigo.setCapacidadePessoa(request.getCapacidadePessoa());
                    existingAbrigo.setLocalizacao(request.getLocalizacao());

                    CadastroAbrigo updatedAbrigo = abrigoService.save(existingAbrigo);

                    AbrigoResponse response = AbrigoResponse.builder()
                            .idCadastroAbrigo(updatedAbrigo.getIdCadastroAbrigo())
                            .nomeAbrigo(updatedAbrigo.getNomeAbrigo())
                            .nomeResponsavel(updatedAbrigo.getNomeResponsavel())
                            .capacidadePessoa(updatedAbrigo.getCapacidadePessoa())
                            .localizacao(updatedAbrigo.getLocalizacao())
                            .build();

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @Override
    public ResponseEntity<Void> delete(@PathVariable Long idAbrigo) throws IdNotFoundException {
        if (abrigoService.findById(idAbrigo).isPresent()) {
            abrigoService.delete(idAbrigo);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/paginacao")
    public ResponseEntity<Page<AbrigoResponse>> findAllPage(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "2") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CadastroAbrigo> pageAbrigo = abrigoService.findAllPage(pageRequest);

        Page<AbrigoResponse> paginacaoAbrigo = pageAbrigo.map(abrigo -> AbrigoResponse.builder()

                .idCadastroAbrigo(abrigo.getIdCadastroAbrigo())
                .nomeAbrigo(abrigo.getNomeAbrigo())
                .nomeResponsavel(abrigo.getNomeResponsavel())
                .capacidadePessoa(abrigo.getCapacidadePessoa())
                .localizacao(abrigo.getLocalizacao())

                .build());

        return ResponseEntity.ok(paginacaoAbrigo);
    }


}
