package br.com.fiap.safehub.service;

import br.com.fiap.safehub.entity.CadastroUsuario;
import br.com.fiap.safehub.repository.CadastroAbrigoRepository;
import br.com.fiap.safehub.repository.CadastroUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CadastroUsuarioService {

    @Autowired
    private CadastroUsuarioRepository usuarioRepository;

    @Autowired
    private CadastroAbrigoRepository abrigoRepository;

    @Cacheable(cacheNames = "usuarios", key = "#idUsuario")
    public List<CadastroUsuario> listAll() {

        return usuarioRepository.listAllById();
    }

    @Cacheable(cacheNames = "usuarios", key = "#idUsuario")
    public Optional<CadastroUsuario> findById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }

    public Page<CadastroUsuario> findAllPage(PageRequest request) {
        return usuarioRepository.findAll(request);
    }

    @CachePut(cacheNames = "usuarios", key = "#usuario.idUsuario")
    public CadastroUsuario save(CadastroUsuario usuario) {
        if (!abrigoRepository.existsById(usuario.getChaveAbrigo().getIdCadastroAbrigo())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Abrigo não encontrado.");
        }

        return usuarioRepository.save(usuario);
    }

    @CacheEvict(cacheNames = "usuarios", key = "#idUsuario")
    public void delete(Long idUsuario) {

        usuarioRepository.deleteById(idUsuario);
    }


    public CadastroUsuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }



}
