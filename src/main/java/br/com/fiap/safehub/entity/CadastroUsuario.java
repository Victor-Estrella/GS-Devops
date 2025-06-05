package br.com.fiap.safehub.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "T_SAFEHUB_CADASTRO_USUARIO")
@SequenceGenerator(name = "usuario", sequenceName = "SQ_T_SAFEHUB_CADASTRO_USUARIO", allocationSize = 1)
public class CadastroUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
    private Long idUsuario;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "senha", length = 15, nullable = false)
    private String senha;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_id_abrigo")
    private CadastroAbrigo chaveAbrigo;



}
