package br.com.fiap.safehub.controller;

import br.com.fiap.safehub.dto.EstoqueResponse;
import br.com.fiap.safehub.dto.LoginEstoqueResponse;
import br.com.fiap.safehub.dto.LoginRequest;
import br.com.fiap.safehub.dto.LoginResponse;
import br.com.fiap.safehub.entity.CadastroUsuario;
import br.com.fiap.safehub.security.JWTUtil;
import br.com.fiap.safehub.service.CadastroUsuarioService;
import br.com.fiap.safehub.service.EstoqueAbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class AutenticacaoController {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CadastroUsuarioService usuarioService;

    @Autowired
    private EstoqueAbrigoService estoqueService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @org.springframework.web.bind.annotation.RequestBody LoginRequest loginRequest) {
        try {

            var authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getSenha()
            );
            authenticationManager.authenticate(authToken);

            String token = jwtUtil.construirToken(loginRequest.getEmail(), loginRequest.getSenha());


            CadastroUsuario usuario = usuarioService.findByEmail(loginRequest.getEmail());
            Long idAbrigo = usuario.getChaveAbrigo().getIdCadastroAbrigo();
            var estoque = estoqueService.buscarAgrupadoPorAbrigo(idAbrigo);


            LoginEstoqueResponse usuarioInfo = LoginEstoqueResponse.builder()
                    .idUsuario(usuario.getIdUsuario())
                    .nome(usuario.getNome())
                    .email(usuario.getEmail())
                    .chaveAbrigo(idAbrigo)
                    .build();

            LoginResponse response = LoginResponse.builder()
                    .token(token)
                    .usuario(usuarioInfo)
                    .build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }
}

