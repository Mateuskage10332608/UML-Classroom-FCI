package br.com.agrodrone.model;

/**
 * Classe base para entidades persistentes.
 */
public abstract class EntidadeBase implements Identificavel {
    private Long id;

    @Override
    public Long obterId() {
        return id;
    }

    public void definirId(Long id) {
        this.id = id;
    }
}
