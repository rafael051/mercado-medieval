package br.com.fiap.mercadomedieval.repository;

import br.com.fiap.mercadomedieval.model.Personagem;
import br.com.fiap.mercadomedieval.model.enums.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PersonagemRepository extends JpaRepository<Personagem, Long>,
        JpaSpecificationExecutor<Personagem> {

    List<Personagem> findByNomeContainingIgnoreCase(String nome);
    List<Personagem> findByClasse(Classe classe);
}
