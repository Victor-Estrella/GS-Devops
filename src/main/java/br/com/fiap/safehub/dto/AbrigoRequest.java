package br.com.fiap.safehub.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AbrigoRequest {

    @NotBlank
    private String nomeAbrigo;

    @NotNull
    private int capacidadePessoa;

    @NotBlank
    private String nomeResponsavel;

    @NotBlank
    private String localizacao;

}
