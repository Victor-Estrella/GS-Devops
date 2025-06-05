package br.com.fiap.safehub.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueRequest {

    private List<ItemEstoqueDTO> alimentos;
    private List<ItemEstoqueDTO> roupas;
    private List<ItemEstoqueDTO> medicamentos;
    private Float litrosAgua;
    private Integer numeroPessoa;
    private Long chaveAbrigo;
}
