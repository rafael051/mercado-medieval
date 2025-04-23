package br.com.fiap.mercadomedieval.model;

import br.com.fiap.mercadomedieval.model.enums.Raridade;
import br.com.fiap.mercadomedieval.model.enums.TipoItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do item é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Tipo do item é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoItem tipo;

    @NotNull(message = "Raridade do item é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Raridade raridade;

    @NotNull(message = "O preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "O preço deve ser igual ou maior que zero")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "personagem_id", nullable = false)
    @NotNull(message = "Todo item precisa estar vinculado a um personagem")
    private Personagem dono;
}
