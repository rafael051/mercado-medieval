package br.com.fiap.mercadomedieval.dto;

import br.com.fiap.mercadomedieval.model.enums.Classe;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * DTO de entrada para criação e atualização de personagens no sistema.
 * Aplica validações de integridade e formato para cada campo.
 */
public record PersonagemDTO(

        @NotBlank(message = "O nome do personagem é obrigatório")
        @Size(max = 100, message = "O nome do personagem deve ter no máximo 100 caracteres")
        String nome,

        @NotNull(message = "A classe do personagem é obrigatória")
        Classe classe,

        @Min(value = 1, message = "O nível mínimo permitido é 1")
        @Max(value = 99, message = "O nível máximo permitido é 99")
        int nivel,

        @NotNull(message = "O campo moedas é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O valor de moedas não pode ser negativo")
        @Digits(integer = 8, fraction = 2, message = "O valor de moedas deve ter no máximo 8 dígitos inteiros e 2 decimais")
        BigDecimal moedas

) {}
