package br.com.fiap.safehub.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.List;

import br.com.fiap.safehub.entity.EstoqueAbrigo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstoqueResponse {


    private List<ItemEstoqueDTO> alimentos;
    private List<ItemEstoqueDTO> roupas;
    private List<ItemEstoqueDTO> medicamentos;
    private float litrosAgua;
    private int numeroPessoa;
    private Long chaveAbrigo;


    public void setAlimentos(List<EstoqueAbrigo> estoques) {
        this.alimentos = estoques.stream()
            .filter(e -> e.getAlimento() != null)
            .map(e -> ItemEstoqueDTO.builder()
                    .idEstoque(e.getIdEstoque())
                    .nome(e.getAlimento())
                    .quantidade(e.getQuantidadeAlimento())
                    .build())
            .toList();
    }

    public void setRoupas(List<EstoqueAbrigo> estoques) {
        this.roupas = estoques.stream()
            .filter(e -> e.getRoupa() != null)
            .map(e -> ItemEstoqueDTO.builder()
                    .idEstoque(e.getIdEstoque())
                    .nome(e.getRoupa())
                    .quantidade(e.getQuantidadeRoupa())
                    .build())
            .toList();
    }

    public void setMedicamentos(List<EstoqueAbrigo> estoques) {
        this.medicamentos = estoques.stream()
            .filter(e -> e.getMedicamento() != null)
            .map(e -> ItemEstoqueDTO.builder()
                    .idEstoque(e.getIdEstoque())
                    .nome(e.getMedicamento())
                    .quantidade(e.getQuantidadeMedicamento())
                    .build())
            .toList();
    }
}
