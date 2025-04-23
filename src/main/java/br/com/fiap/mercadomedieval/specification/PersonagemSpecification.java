package br.com.fiap.mercadomedieval.specification;

import br.com.fiap.mercadomedieval.dto.PersonagemFilter;
import br.com.fiap.mercadomedieval.model.Personagem;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Specification para criação de filtros dinâmicos na listagem de personagens.
 */
public class PersonagemSpecification {

    public static Specification<Personagem> withFilters(PersonagemFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 🔍 Filtro por nome (case-insensitive com LIKE)
            if (filter.nome() != null && !filter.nome().isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("nome")),
                        "%" + filter.nome().toLowerCase() + "%"
                ));
            }

            // 🛡️ Filtro por classe (enum)
            if (filter.classe() != null) {
                predicates.add(cb.equal(root.get("classe"), filter.classe()));
            }

            // Retorna todos os filtros combinados com AND
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
