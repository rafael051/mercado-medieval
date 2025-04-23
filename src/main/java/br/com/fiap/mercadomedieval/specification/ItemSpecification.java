package br.com.fiap.mercadomedieval.specification;

import br.com.fiap.mercadomedieval.dto.ItemFilter;
import br.com.fiap.mercadomedieval.model.Item;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Cria uma Specification dinâmica para consulta filtrada de Itens.
 */
public class ItemSpecification {

    public static Specification<Item> withFilters(ItemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔍 Filtro por nome (like, case-insensitive)
            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nome")),
                        "%" + filter.nome().toLowerCase() + "%"
                ));
            }

            // 🎯 Filtro por tipo de item (enum)
            if (filter.tipo() != null) {
                predicates.add(cb.equal(root.get("tipo"), filter.tipo()));
            }

            // 🧬 Filtro por raridade do item (enum)
            if (filter.raridade() != null) {
                predicates.add(cb.equal(root.get("raridade"), filter.raridade()));
            }

            // 💰 Filtros de faixa de preço
            if (filter.precoMin() != null && filter.precoMax() != null) {
                predicates.add(cb.between(root.get("preco"), filter.precoMin(), filter.precoMax()));
            } else if (filter.precoMin() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("preco"), filter.precoMin()));
            } else if (filter.precoMax() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("preco"), filter.precoMax()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
