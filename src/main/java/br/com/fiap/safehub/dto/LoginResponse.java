package br.com.fiap.safehub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginResponse {
    private String token;
    private LoginEstoqueResponse usuario;
}
