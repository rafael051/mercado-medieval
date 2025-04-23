package br.com.fiap.mercadomedieval.controller;

import br.com.fiap.mercadomedieval.dto.ItemDTO;
import br.com.fiap.mercadomedieval.dto.ItemFilter;
import br.com.fiap.mercadomedieval.exception.EntidadeNaoEncontradaException;
import br.com.fiap.mercadomedieval.model.Item;
import br.com.fiap.mercadomedieval.model.enums.Raridade;
import br.com.fiap.mercadomedieval.model.enums.TipoItem;
import br.com.fiap.mercadomedieval.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/itens")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService service;

    /**
     * Cria um novo item a partir do DTO.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item salvar(@RequestBody @Valid ItemDTO dto) {
        return service.salvar(dto);
    }

    /**
     * Lista itens com filtros opcionais.
     * Suporta filtro por nome, tipo, raridade, e faixa de preço.
     */
    @GetMapping
    public Page<Item> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String raridade,
            @RequestParam(required = false) String precoMin,
            @RequestParam(required = false) String precoMax,
            Pageable pageable
    ) {
        // Conversão segura dos filtros
        TipoItem tipoEnum = parseEnum(TipoItem.class, tipo);
        Raridade raridadeEnum = parseEnum(Raridade.class, raridade);
        BigDecimal precoMinValue = parseBigDecimal(precoMin);
        BigDecimal precoMaxValue = parseBigDecimal(precoMax);

        var filtro = new ItemFilter(nome, tipoEnum, raridadeEnum, precoMinValue, precoMaxValue);
        return service.listar(filtro, pageable);
    }

    /**
     * Busca um item pelo ID ou lança 404 caso não encontrado.
     */
    @GetMapping("/{id}")
    public Item buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Item com ID " + id + " não encontrado."));
    }

    /**
     * Remove um item pelo ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    // 🔁 Métodos utilitários para conversão segura de strings

    private <T extends Enum<T>> T parseEnum(Class<T> enumClass, String value) {
        try {
            return (value != null) ? Enum.valueOf(enumClass, value.toUpperCase()) : null;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valor inválido para " + enumClass.getSimpleName() + ": " + value);
        }
    }

    private BigDecimal parseBigDecimal(String value) {
        try {
            return (value != null) ? new BigDecimal(value) : null;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Valor numérico inválido: " + value);
        }
    }
}
