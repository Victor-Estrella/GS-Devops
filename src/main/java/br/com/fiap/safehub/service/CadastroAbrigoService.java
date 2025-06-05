package br.com.fiap.safehub.service;

import br.com.fiap.safehub.entity.CadastroAbrigo;
import br.com.fiap.safehub.repository.CadastroAbrigoRepository;
import br.com.fiap.safehub.repository.EstoqueAbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroAbrigoService {

    @Autowired
    private CadastroAbrigoRepository abrigoRepository;

    @Autowired
    private EstoqueAbrigoRepository estoqueRepository;

    @Autowired
    private CadastroAbrigoRepository usuarioRepository;

    @Cacheable(cacheNames = "abrigos", key = "#idAbrigo")
    public List<CadastroAbrigo> findAll() {
        return abrigoRepository.listAllById();
    }

    @Cacheable(cacheNames = "abrigos", key = "#idAbrigo")
    public Optional<CadastroAbrigo> findById(Long idAbrigo) {
        return abrigoRepository.findById(idAbrigo);
    }

    public Page<CadastroAbrigo> findAllPage(PageRequest request) {
        return abrigoRepository.findAll(request);
    }

    @CachePut(cacheNames = "abrigos", key = "#abrigo.idAbrigo")
    public CadastroAbrigo save(CadastroAbrigo cadastroAbrigo) {

        return abrigoRepository.save(cadastroAbrigo);
    }

    @Transactional
    @CacheEvict(cacheNames = "abrigos", key = "#idAbrigo")
    public void delete(Long idAbrigo) {
        try {
            estoqueRepository.deleteByChaveAbrigo_IdCadastroAbrigo(idAbrigo);
            usuarioRepository.deleteById(idAbrigo);
            abrigoRepository.deleteById(idAbrigo);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Ainda existem dependências relacionadas a este abrigo. Verifique todas as tabelas filhas.", e);
        }
    }

}
