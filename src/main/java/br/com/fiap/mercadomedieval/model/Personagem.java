package br.com.fiap.mercadomedieval.model;

import br.com.fiap.mercadomedieval.model.enums.Classe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_personagem")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do personagem é obrigatório")
    @Size(max = 100, message = "O nome do personagem deve ter no máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotNull(message = "A classe do personagem é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Classe classe;

    @Min(value = 1, message = "O nível mínimo é 1")
    @Max(value = 99, message = "O nível máximo é 99")
    @Column(nullable = false)
    private int nivel;

    @NotNull(message = "O campo moedas é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "Moedas não pode ser negativa")
    @Digits(integer = 8, fraction = 2, message = "O valor de moedas deve ter no máximo 8 dígitos inteiros e 2 decimais")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal moedas;
}
