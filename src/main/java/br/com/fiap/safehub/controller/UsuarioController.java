package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.*;
import br.com.fiap.safehub.entity.CadastroAbrigo;
import br.com.fiap.safehub.entity.CadastroUsuario;
import br.com.fiap.safehub.exception.IdNotFoundException;
import br.com.fiap.safehub.service.CadastroUsuarioService;
import br.com.fiap.safehub.service.EstoqueAbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UsuarioController implements UsuarioApi{

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private EstoqueAbrigoService estoqueService;



    @Override
    public ResponseEntity<List<UsuarioResponse>> listAll() {

        List<UsuarioResponse> response = usuarioService.listAll().stream()
                .map(u -> new UsuarioResponse(
                        u.getIdUsuario(),
                        u.getNome(),
                        u.getEmail(),
                        u.getSenha(),
                        u.getChaveAbrigo().getIdCadastroAbrigo()
                ))
                .toList();

        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<UsuarioResponse> getById(@PathVariable Long idUsuario) throws IdNotFoundException {

        return usuarioService.findById(idUsuario)
                .map(u -> new UsuarioResponse(
                        u.getIdUsuario(),
                        u.getNome(),
                        u.getEmail(),
                        u.getSenha(),
                        u.getChaveAbrigo().getIdCadastroAbrigo()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @Override
    public ResponseEntity<UsuarioResponse> create(@Valid @RequestBody UsuarioRequest request) {

        CadastroUsuario usuario = CadastroUsuario.builder()
                .nome(request.getNome())
                .email(request.getEmail())
                .senha(request.getSenha())
                .chaveAbrigo(CadastroAbrigo.builder()
                        .idCadastroAbrigo(request.getChaveAbrigo())
                        .build())
                .build();

        CadastroUsuario savedUsuario = usuarioService.save(usuario);

        UsuarioResponse response = UsuarioResponse.builder()

                .idUsuario(savedUsuario.getIdUsuario())
                .nome(savedUsuario.getNome())
                .email(savedUsuario.getEmail())
                .senha(savedUsuario.getSenha())
                .chaveAbrigo(savedUsuario.getChaveAbrigo().getIdCadastroAbrigo())

                .build();

        return ResponseEntity.ok(response);


    }

    @Override
    public ResponseEntity<UsuarioResponse> update(@PathVariable Long idUsuario, @Valid @RequestBody UsuarioRequest request) {
        return usuarioService.findById(idUsuario)
                .map(existingUsuario -> {


                    existingUsuario.setNome(request.getNome());
                    existingUsuario.setEmail(request.getEmail());
                    existingUsuario.setSenha(request.getSenha());
                    existingUsuario.setChaveAbrigo(CadastroAbrigo.builder()
                                    .idCadastroAbrigo(request.getChaveAbrigo())
                            .build());

                    CadastroUsuario updatedUsuario = usuarioService.save(existingUsuario);

                    UsuarioResponse response = UsuarioResponse.builder()

                            .idUsuario(updatedUsuario.getIdUsuario())
                            .nome(updatedUsuario.getNome())
                            .email(updatedUsuario.getEmail())
                            .senha(updatedUsuario.getSenha())
                            .chaveAbrigo(updatedUsuario.getChaveAbrigo().getIdCadastroAbrigo())

                            .build();

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @Override
    public ResponseEntity<Void> delete(@PathVariable Long idUsuario) throws IdNotFoundException {
        CadastroUsuario usuario = usuarioService.findById(idUsuario).orElse(null);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuarioService.delete(usuario.getIdUsuario());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/paginacao")
    public ResponseEntity<Page<UsuarioResponse>> findAllPage(
            @RequestParam(value = "pagina", defaultValue = "0") Integer page,
            @RequestParam(value = "tamanho", defaultValue = "5") Integer size) {

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CadastroUsuario> pageUsuario = usuarioService.findAllPage(pageRequest);

        Page<UsuarioResponse> paginacaoUsuario = pageUsuario.map(u -> UsuarioResponse.builder()
                .idUsuario(u.getIdUsuario())
                .nome(u.getNome())
                .email(u.getEmail())
                .senha(u.getSenha())
                .chaveAbrigo(u.getChaveAbrigo().getIdCadastroAbrigo())
                .build());

        return ResponseEntity.ok(paginacaoUsuario);
    }


}
