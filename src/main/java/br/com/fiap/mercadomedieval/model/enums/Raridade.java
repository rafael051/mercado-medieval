package br.com.fiap.mercadomedieval.model.enums;

public enum Raridade {
    COMUM(1),
    RARO(2),
    EPICO(3),
    LENDARIO(4);

    private final int nivel;

    Raridade(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel() {
        return nivel;
    }
}
