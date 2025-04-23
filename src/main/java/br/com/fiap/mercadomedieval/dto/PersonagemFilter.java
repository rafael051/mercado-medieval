package br.com.fiap.mercadomedieval.dto;

import br.com.fiap.mercadomedieval.model.enums.Classe;

/**
 * DTO usado para filtros dinâmicos na listagem de personagens.
 * Todos os campos são opcionais e podem ser combinados.
 */
public record PersonagemFilter(
        String nome,
        Classe classe
) {}
