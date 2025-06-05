package br.com.fiap.safehub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbrigoResponse {


    private Long idCadastroAbrigo;
    private String nomeAbrigo;
    private int capacidadePessoa;
    private String nomeResponsavel;
    private String localizacao;

}
