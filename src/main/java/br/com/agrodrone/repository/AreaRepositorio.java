package br.com.agrodrone.repository;

import java.util.HashMap;
import java.util.Map;

import br.com.agrodrone.domain.AreaAgricola;

/**
 * Repositório em memória para áreas agrícolas.
 */
public class AreaRepositorio {
    private final Map<Integer, AreaAgricola> banco = new HashMap<>();

    public AreaAgricola buscarPorId(int id) {
        return banco.get(id);
    }

    public void salvar(AreaAgricola area) {
        banco.put(area.getId(), area);
    }
}
