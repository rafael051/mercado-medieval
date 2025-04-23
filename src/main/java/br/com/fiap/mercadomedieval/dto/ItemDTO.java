package br.com.fiap.mercadomedieval.dto;

import br.com.fiap.mercadomedieval.model.enums.Raridade;
import br.com.fiap.mercadomedieval.model.enums.TipoItem;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO de entrada para criação de um item, vinculado a um personagem.
 */
public record ItemDTO(

        @NotBlank(message = "O nome do item é obrigatório")
        String nome,

        @NotNull(message = "O tipo do item é obrigatório")
        TipoItem tipo,

        @NotNull(message = "A raridade do item é obrigatória")
        Raridade raridade,

        @NotNull(message = "O preço do item é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O preço deve ser igual ou maior que zero")
        BigDecimal preco,

        @NotNull(message = "O ID do personagem dono do item é obrigatório")
        Long personagemId

) {}
