package br.com.fiap.safehub.repository;

import br.com.fiap.safehub.entity.CadastroAbrigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CadastroAbrigoRepository extends JpaRepository<CadastroAbrigo, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM  t_safehub_cadastro_abrigo ORDER BY ID_CADASTRO_ABRIGO ASC")
    List<CadastroAbrigo> listAllById();

}
