package br.com.fiap.safehub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemEstoqueDTO {
    private Long idEstoque;
    private String nome;
    private float quantidade;
}
