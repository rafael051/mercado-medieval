package br.com.fiap.mercadomedieval.controller;

import br.com.fiap.mercadomedieval.dto.PersonagemDTO;
import br.com.fiap.mercadomedieval.dto.PersonagemFilter;
import br.com.fiap.mercadomedieval.exception.EntidadeNaoEncontradaException;
import br.com.fiap.mercadomedieval.model.Personagem;
import br.com.fiap.mercadomedieval.model.enums.Classe;
import br.com.fiap.mercadomedieval.service.PersonagemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personagens")
@RequiredArgsConstructor
public class PersonagemController {

    private final PersonagemService service;

    /**
     * Cria um novo personagem a partir do DTO.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Personagem salvar(@RequestBody @Valid PersonagemDTO dto) {
        return service.salvar(dto);
    }

    /**
     * Lista personagens com filtros opcionais (nome e classe).
     */
    @GetMapping
    public Page<Personagem> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Classe classe,
            Pageable pageable
    ) {
        var filtro = new PersonagemFilter(nome, classe);
        return service.listar(filtro, pageable);
    }

    /**
     * Retorna um personagem por ID ou lança exceção 404 caso não encontrado.
     */
    @GetMapping("/{id}")
    public Personagem buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Personagem com ID " + id + " não encontrado."));
    }

    /**
     * Exclui um personagem pelo ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
