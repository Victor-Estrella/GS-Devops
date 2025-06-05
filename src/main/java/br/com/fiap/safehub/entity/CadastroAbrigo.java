package br.com.fiap.safehub.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "T_SAFEHUB_CADASTRO_ABRIGO")
@SequenceGenerator(name = "abrigo", sequenceName = "SQ_T_SAFEHUB_CADASTRO_ABRIGO", allocationSize = 1)
public class CadastroAbrigo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "abrigo")
    private Long idCadastroAbrigo;

    @Column(name = "nome_abrigo", length = 100, nullable = false)
    private String nomeAbrigo;

    @Column(name = "capacidade_pessoa", nullable = false)
    private int capacidadePessoa;

    @Column(name = "nm_responsavel", length = 100, nullable = false)
    private String nomeResponsavel;

    @Column(name = "endereco", length = 100, nullable = false)
    private String localizacao;

    public CadastroAbrigo(Long idCadastroAbrigo) {
        this.idCadastroAbrigo = idCadastroAbrigo;
    }
}

