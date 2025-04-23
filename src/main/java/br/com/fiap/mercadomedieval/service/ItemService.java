package br.com.fiap.mercadomedieval.service;

import br.com.fiap.mercadomedieval.dto.ItemDTO;
import br.com.fiap.mercadomedieval.dto.ItemFilter;
import br.com.fiap.mercadomedieval.model.Item;
import br.com.fiap.mercadomedieval.model.Personagem;
import br.com.fiap.mercadomedieval.repository.ItemRepository;
import br.com.fiap.mercadomedieval.repository.PersonagemRepository;
import br.com.fiap.mercadomedieval.specification.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final PersonagemRepository personagemRepository;

    /**
     * Salva um novo item com base nos dados do DTO.
     * @param dto dados do item a ser criado
     * @return item salvo no banco
     */
    public Item salvar(ItemDTO dto) {
        Personagem dono = personagemRepository.findById(dto.personagemId())
                .orElseThrow(() -> new RuntimeException("Personagem com ID " + dto.personagemId() + " não encontrado"));

        Item item = Item.builder()
                .nome(dto.nome())
                .tipo(dto.tipo())
                .raridade(dto.raridade())
                .preco(dto.preco())
                .dono(dono)
                .build();

        return itemRepository.save(item);
    }

    /**
     * Lista itens aplicando filtros dinâmicos de nome, tipo, raridade e faixa de preço.
     */
    public Page<Item> listar(ItemFilter filter, Pageable pageable) {
        Specification<Item> spec = ItemSpecification.withFilters(filter);
        return itemRepository.findAll(spec, pageable);
    }

    /**
     * Busca um item por ID.
     */
    public Optional<Item> buscarPorId(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * Exclui um item pelo ID.
     */
    public void excluir(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item com ID " + id + " não encontrado para exclusão.");
        }
        itemRepository.deleteById(id);
    }
}
