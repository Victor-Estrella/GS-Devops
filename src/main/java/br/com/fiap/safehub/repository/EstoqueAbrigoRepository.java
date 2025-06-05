package br.com.fiap.safehub.repository;


import br.com.fiap.safehub.entity.EstoqueAbrigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueAbrigoRepository extends JpaRepository<EstoqueAbrigo, Long> {

    List<EstoqueAbrigo> findByChaveAbrigo_IdCadastroAbrigo(Long idCadastroAbrigo);

    void deleteByChaveAbrigo_IdCadastroAbrigo(Long idCadastroAbrigo);

}
