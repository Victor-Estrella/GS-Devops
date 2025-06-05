package br.com.fiap.safehub.service;

import br.com.fiap.safehub.dto.EstoqueRequest;
import br.com.fiap.safehub.dto.EstoqueResponse;
import br.com.fiap.safehub.dto.ItemEstoqueDTO;
import br.com.fiap.safehub.entity.CadastroAbrigo;
import br.com.fiap.safehub.entity.EstoqueAbrigo;
import br.com.fiap.safehub.repository.CadastroAbrigoRepository;
import br.com.fiap.safehub.repository.EstoqueAbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EstoqueAbrigoService {

    @Autowired
    private EstoqueAbrigoRepository estoqueRepository;

    @Autowired
    private CadastroAbrigoRepository abrigoRepository;

    public Optional<EstoqueAbrigo> findById(Long idEstoque) {
        return estoqueRepository.findById(idEstoque);
    }

    public EstoqueResponse buscarAgrupadoPorAbrigo(Long idAbrigo) {
        List<EstoqueAbrigo> estoques = estoqueRepository.findByChaveAbrigo_IdCadastroAbrigo(idAbrigo);

        List<ItemEstoqueDTO> alimentos = estoques.stream()
                .filter(e -> e.getAlimento() != null)
                .map(e -> ItemEstoqueDTO.builder()
                        .idEstoque(e.getIdEstoque())
                        .nome(e.getAlimento())
                        .quantidade(e.getQuantidadeAlimento())
                        .build())
                .toList();

        List<ItemEstoqueDTO> roupas = estoques.stream()
                .filter(e -> e.getRoupa() != null)
                .map(e -> ItemEstoqueDTO.builder()
                        .idEstoque(e.getIdEstoque())
                        .nome(e.getRoupa())
                        .quantidade(e.getQuantidadeRoupa())
                        .build())
                .toList();

        List<ItemEstoqueDTO> medicamentos = estoques.stream()
                .filter(e -> e.getMedicamento() != null)
                .map(e -> ItemEstoqueDTO.builder()
                        .idEstoque(e.getIdEstoque())
                        .nome(e.getMedicamento())
                        .quantidade(e.getQuantidadeMedicamento())
                        .build())
                .toList();

        float litrosAgua = estoques.stream()
                .map(EstoqueAbrigo::getLitrosAgua)
                .findFirst()
                .orElse(0f);

        int numeroPessoa = estoques.stream()
                .map(EstoqueAbrigo::getNumeroPessoa)
                .findFirst()
                .orElse(0);

        return EstoqueResponse.builder()

                .alimentos(alimentos)
                .roupas(roupas)
                .medicamentos(medicamentos)
                .litrosAgua(litrosAgua)
                .numeroPessoa(numeroPessoa)
                .chaveAbrigo(idAbrigo)

                .build();
    }


    public List<EstoqueAbrigo> create(Long idAbrigo, EstoqueRequest request) {
        CadastroAbrigo abrigo = abrigoRepository.findById(idAbrigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Abrigo não encontrado."));

        List<EstoqueAbrigo> criados = new ArrayList<>();

        if (request.getAlimentos() != null) {
            for (ItemEstoqueDTO alimento : request.getAlimentos()) {
                EstoqueAbrigo novo = EstoqueAbrigo.builder()

                        .alimento(alimento.getNome())
                        .quantidadeAlimento(alimento.getQuantidade())
                        .litrosAgua(request.getLitrosAgua())
                        .numeroPessoa(request.getNumeroPessoa())
                        .chaveAbrigo(abrigo)

                        .build();

                criados.add(estoqueRepository.save(novo));
            }
        }
        if (request.getRoupas() != null) {
            for (ItemEstoqueDTO roupa : request.getRoupas()) {
                EstoqueAbrigo novo = EstoqueAbrigo.builder()

                        .roupa(roupa.getNome())
                        .quantidadeRoupa(roupa.getQuantidade())
                        .litrosAgua(request.getLitrosAgua())
                        .numeroPessoa(request.getNumeroPessoa())
                        .chaveAbrigo(abrigo)

                        .build();

                criados.add(estoqueRepository.save(novo));
            }
        }
        if (request.getMedicamentos() != null) {
            for (ItemEstoqueDTO medicamento : request.getMedicamentos()) {
                EstoqueAbrigo novo = EstoqueAbrigo.builder()

                        .medicamento(medicamento.getNome())
                        .quantidadeMedicamento(medicamento.getQuantidade())
                        .litrosAgua(request.getLitrosAgua())
                        .numeroPessoa(request.getNumeroPessoa())
                        .chaveAbrigo(abrigo)

                        .build();

                criados.add(estoqueRepository.save(novo));
            }
        }

        return criados;
    }

    public void deletar(Long idEstoque) {
        if (!estoqueRepository.existsById(idEstoque)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estoque não encontrado.");
        }
        estoqueRepository.deleteById(idEstoque);
    }

    @Transactional
    public void deleteIdAbrigo(Long idAbrigo) {
        if (!abrigoRepository.existsById(idAbrigo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Abrigo não encontrado.");
        }
        estoqueRepository.deleteByChaveAbrigo_IdCadastroAbrigo(idAbrigo);
    }

    public void updateEstoque(Long idAbrigo, EstoqueRequest request) {

        List<EstoqueAbrigo> existentes = estoqueRepository.findByChaveAbrigo_IdCadastroAbrigo(idAbrigo);



        List<String> nomesAlimentos = request.getAlimentos().stream().map(ItemEstoqueDTO::getNome).toList();
        for (ItemEstoqueDTO alimento : request.getAlimentos()) {
            EstoqueAbrigo existente = null;
            if (alimento.getIdEstoque() != null) {
                existente = existentes.stream()
                        .filter(e -> alimento.getIdEstoque().equals(e.getIdEstoque()))
                        .findFirst()
                        .orElse(null);
            }
            if (existente == null) {
                existente = existentes.stream()
                        .filter(e -> alimento.getNome().equals(e.getAlimento()))
                        .findFirst()
                        .orElse(null);
            }
            if (existente != null) {
                existente.setAlimento(alimento.getNome());
                existente.setQuantidadeAlimento(alimento.getQuantidade());
                existente.setLitrosAgua(request.getLitrosAgua());
                existente.setNumeroPessoa(request.getNumeroPessoa());
                estoqueRepository.save(existente);
            } else {
                EstoqueAbrigo novo = EstoqueAbrigo.builder()
                        .alimento(alimento.getNome())
                        .quantidadeAlimento(alimento.getQuantidade())
                        .litrosAgua(request.getLitrosAgua())
                        .numeroPessoa(request.getNumeroPessoa())
                        .chaveAbrigo(new CadastroAbrigo(idAbrigo))
                        .build();
                estoqueRepository.save(novo);
            }
        }


        existentes.stream()
                .filter(e -> e.getAlimento() != null && !nomesAlimentos.contains(e.getAlimento()))
                .forEach(estoqueRepository::delete);

        List<String> nomesRoupas = request.getRoupas().stream().map(ItemEstoqueDTO::getNome).toList();
        for (ItemEstoqueDTO roupa : request.getRoupas()) {
            EstoqueAbrigo existente = null;
            if (roupa.getIdEstoque() != null) {
                existente = existentes.stream()
                        .filter(e -> roupa.getIdEstoque().equals(e.getIdEstoque()))
                        .findFirst()
                        .orElse(null);
            }
            if (existente == null) {
                existente = existentes.stream()
                        .filter(e -> roupa.getNome().equals(e.getRoupa()))
                        .findFirst()
                        .orElse(null);
            }
            if (existente != null) {
                existente.setRoupa(roupa.getNome());
                existente.setQuantidadeRoupa(roupa.getQuantidade());
                existente.setLitrosAgua(request.getLitrosAgua());
                existente.setNumeroPessoa(request.getNumeroPessoa());
                estoqueRepository.save(existente);
            } else {
                EstoqueAbrigo novo = EstoqueAbrigo.builder()
                        .roupa(roupa.getNome())
                        .quantidadeRoupa(roupa.getQuantidade())
                        .litrosAgua(request.getLitrosAgua())
                        .numeroPessoa(request.getNumeroPessoa())
                        .chaveAbrigo(new CadastroAbrigo(idAbrigo))
                        .build();
                estoqueRepository.save(novo);
            }
        }
        existentes.stream()
                .filter(e -> e.getRoupa() != null && !nomesRoupas.contains(e.getRoupa()))
                .forEach(estoqueRepository::delete);

        List<String> nomesMedicamentos = request.getMedicamentos().stream().map(ItemEstoqueDTO::getNome).toList();
        for (ItemEstoqueDTO medicamento : request.getMedicamentos()) {
            EstoqueAbrigo existente = null;
            if (medicamento.getIdEstoque() != null) {
                existente = existentes.stream()
                        .filter(e -> medicamento.getIdEstoque().equals(e.getIdEstoque()))
                        .findFirst()
                        .orElse(null);
            }
            if (existente != null) {
                existente.setMedicamento(medicamento.getNome());
                existente.setQuantidadeMedicamento(medicamento.getQuantidade());
                existente.setLitrosAgua(request.getLitrosAgua());
                existente.setNumeroPessoa(request.getNumeroPessoa());
                estoqueRepository.save(existente);
            } else {
                EstoqueAbrigo novo = EstoqueAbrigo.builder()
                        .medicamento(medicamento.getNome())
                        .quantidadeMedicamento(medicamento.getQuantidade())
                        .litrosAgua(request.getLitrosAgua())
                        .numeroPessoa(request.getNumeroPessoa())
                        .chaveAbrigo(new CadastroAbrigo(idAbrigo))
                        .build();
                estoqueRepository.save(novo);
            }
        }
        existentes.stream()
                .filter(e -> e.getMedicamento() != null && !nomesMedicamentos.contains(e.getMedicamento()))
                .forEach(estoqueRepository::delete);
    }

    public void patchEstoque(Long idAbrigo, EstoqueRequest request) {
        List<EstoqueAbrigo> existentes = estoqueRepository.findByChaveAbrigo_IdCadastroAbrigo(idAbrigo);


        // Alimentos
        if (request.getAlimentos() != null) {
            for (ItemEstoqueDTO alimento : request.getAlimentos()) {
                EstoqueAbrigo existente = null;
                if (alimento.getIdEstoque() != null) {
                    existente = existentes.stream()
                            .filter(e -> alimento.getIdEstoque().equals(e.getIdEstoque()))
                            .findFirst()
                            .orElse(null);
                }
                if (existente == null) {
                    existente = existentes.stream()
                            .filter(e -> alimento.getNome().equals(e.getAlimento()))
                            .findFirst()
                            .orElse(null);
                }
                if (existente != null) {
                    existente.setAlimento(alimento.getNome());
                    existente.setQuantidadeAlimento(alimento.getQuantidade());
                    if (request.getLitrosAgua() != null) existente.setLitrosAgua(request.getLitrosAgua());
                    if (request.getNumeroPessoa() != null) existente.setNumeroPessoa(request.getNumeroPessoa());
                    estoqueRepository.save(existente);
                } else {
                    EstoqueAbrigo novo = EstoqueAbrigo.builder()
                            .alimento(alimento.getNome())
                            .quantidadeAlimento(alimento.getQuantidade())
                            .litrosAgua(request.getLitrosAgua())
                            .numeroPessoa(request.getNumeroPessoa())
                            .chaveAbrigo(new CadastroAbrigo(idAbrigo))
                            .build();
                    estoqueRepository.save(novo);
                }
            }
        }

        // Roupas
        if (request.getRoupas() != null) {
            for (ItemEstoqueDTO roupa : request.getRoupas()) {
                EstoqueAbrigo existente = null;
                if (roupa.getIdEstoque() != null) {
                    existente = existentes.stream()
                            .filter(e -> roupa.getIdEstoque().equals(e.getIdEstoque()))
                            .findFirst()
                            .orElse(null);
                }
                if (existente == null) {
                    existente = existentes.stream()
                            .filter(e -> roupa.getNome().equals(e.getRoupa()))
                            .findFirst()
                            .orElse(null);
                }
                if (existente != null) {
                    existente.setRoupa(roupa.getNome());
                    existente.setQuantidadeRoupa(roupa.getQuantidade());
                    if (request.getLitrosAgua() != null) existente.setLitrosAgua(request.getLitrosAgua());
                    if (request.getNumeroPessoa() != null) existente.setNumeroPessoa(request.getNumeroPessoa());
                    estoqueRepository.save(existente);
                } else {
                    EstoqueAbrigo novo = EstoqueAbrigo.builder()
                            .roupa(roupa.getNome())
                            .quantidadeRoupa(roupa.getQuantidade())
                            .litrosAgua(request.getLitrosAgua())
                            .numeroPessoa(request.getNumeroPessoa())
                            .chaveAbrigo(new CadastroAbrigo(idAbrigo))
                            .build();
                    estoqueRepository.save(novo);
                }
            }
        }

        // Medicamentos
        if (request.getMedicamentos() != null) {
            for (ItemEstoqueDTO medicamento : request.getMedicamentos()) {
                EstoqueAbrigo existente = null;
                if (medicamento.getIdEstoque() != null) {
                    existente = existentes.stream()
                            .filter(e -> medicamento.getIdEstoque().equals(e.getIdEstoque()))
                            .findFirst()
                            .orElse(null);
                }
                if (existente == null) {
                    existente = existentes.stream()
                            .filter(e -> medicamento.getNome().equals(e.getMedicamento()))
                            .findFirst()
                            .orElse(null);
                }
                if (existente != null) {
                    existente.setMedicamento(medicamento.getNome());
                    existente.setQuantidadeMedicamento(medicamento.getQuantidade());
                    if (request.getLitrosAgua() != null) existente.setLitrosAgua(request.getLitrosAgua());
                    if (request.getNumeroPessoa() != null) existente.setNumeroPessoa(request.getNumeroPessoa());
                    estoqueRepository.save(existente);
                } else {
                    EstoqueAbrigo novo = EstoqueAbrigo.builder()
                            .medicamento(medicamento.getNome())
                            .quantidadeMedicamento(medicamento.getQuantidade())
                            .litrosAgua(request.getLitrosAgua())
                            .numeroPessoa(request.getNumeroPessoa())
                            .chaveAbrigo(new CadastroAbrigo(idAbrigo))
                            .build();
                    estoqueRepository.save(novo);
                }
            }
        }

        // Atualização global de litrosAgua e numeroPessoa (caso não tenha itens no PATCH)
        if (request.getLitrosAgua() != null &&
                request.getAlimentos() == null &&
                request.getRoupas() == null &&
                request.getMedicamentos() == null) {
            existentes.forEach(e -> {
                e.setLitrosAgua(request.getLitrosAgua());
                estoqueRepository.save(e);
            });
        }
        if (request.getNumeroPessoa() != null &&
                request.getAlimentos() == null &&
                request.getRoupas() == null &&
                request.getMedicamentos() == null) {
            existentes.forEach(e -> {
                e.setNumeroPessoa(request.getNumeroPessoa());
                estoqueRepository.save(e);
            });
        }
    }
}
