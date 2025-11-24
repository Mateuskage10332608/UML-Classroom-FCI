package br.com.agrodrone.model;

import br.com.agrodrone.domain.enums.Papel;

/**
 * Interface para verificação de papéis.
 */
public interface Autorizavel {
    boolean possuiPapel(Papel papel);
}
