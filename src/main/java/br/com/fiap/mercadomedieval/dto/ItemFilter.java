package br.com.fiap.mercadomedieval.dto;

import br.com.fiap.mercadomedieval.model.enums.Raridade;
import br.com.fiap.mercadomedieval.model.enums.TipoItem;

import java.math.BigDecimal;

/**
 * DTO usado para filtros dinâmicos de busca de itens.
 * Todos os campos são opcionais e podem ser combinados livremente.
 */
public record ItemFilter(
        String nome,
        TipoItem tipo,
        Raridade raridade,
        BigDecimal precoMin,
        BigDecimal precoMax
) {}
