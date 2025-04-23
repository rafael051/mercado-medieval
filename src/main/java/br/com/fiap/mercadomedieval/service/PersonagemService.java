package br.com.fiap.mercadomedieval.service;

import br.com.fiap.mercadomedieval.dto.PersonagemDTO;
import br.com.fiap.mercadomedieval.dto.PersonagemFilter;
import br.com.fiap.mercadomedieval.model.Personagem;
import br.com.fiap.mercadomedieval.repository.PersonagemRepository;
import br.com.fiap.mercadomedieval.specification.PersonagemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonagemService {

    private final PersonagemRepository repository;

    /**
     * Salva um novo personagem com base nos dados do DTO.
     */
    public Personagem salvar(PersonagemDTO dto) {
        Personagem personagem = Personagem.builder()
                .nome(dto.nome())
                .classe(dto.classe())
                .nivel(dto.nivel())
                .moedas(dto.moedas())
                .build();

        return repository.save(personagem);
    }

    /**
     * Lista os personagens aplicando filtros dinâmicos.
     */
    public Page<Personagem> listar(PersonagemFilter filtro, Pageable pageable) {
        Specification<Personagem> spec = PersonagemSpecification.withFilters(filtro);
        return repository.findAll(spec, pageable);
    }

    /**
     * Busca um personagem por ID.
     */
    public Optional<Personagem> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * Exclui um personagem pelo ID.
     */
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Personagem com ID " + id + " não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }
}
