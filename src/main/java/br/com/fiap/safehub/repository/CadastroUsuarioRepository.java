package br.com.fiap.safehub.repository;

import br.com.fiap.safehub.entity.CadastroUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CadastroUsuarioRepository extends JpaRepository<CadastroUsuario, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM  t_safehub_cadastro_usuario ORDER BY id_usuario ASC")
    List<CadastroUsuario> listAllById();

    boolean existsByEmailAndSenha(String email, String senha);

    Optional<CadastroUsuario> findByEmail(String email);

}
