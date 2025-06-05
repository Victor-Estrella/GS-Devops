package br.com.fiap.safehub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponse {

    private Long idUsuario;
    private String nome;
    private String email;
    private String senha;
    private Long chaveAbrigo;


}
