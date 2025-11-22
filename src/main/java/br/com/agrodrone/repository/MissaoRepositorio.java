package br.com.agrodrone.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.agrodrone.domain.Missao;

/**
 * Repositório em memória para missões.
 */
public class MissaoRepositorio {
    private final List<Missao> missoes = new ArrayList<>();
    private int sequenciaId = 1;

    public List<Missao> buscarSobreposicoes(int droneId,
                                            LocalDateTime inicio,
                                            LocalDateTime fim) {
        List<Missao> sobrepostas = new ArrayList<>();
        for (Missao m : missoes) {
            if (m.getDrone() != null && m.getDrone().getId() == droneId) {
                LocalDateTime inicioExistente = m.getDataHora();
                LocalDateTime fimExistente = inicioExistente.plusHours(1);
                boolean sobrepoe = inicio.isBefore(fimExistente)
                                   && fim.isAfter(inicioExistente);
                if (sobrepoe) {
                    sobrepostas.add(m);
                }
            }
        }
        return sobrepostas;
    }

    public synchronized int salvar(Missao missao) {
        int id = sequenciaId++;
        missao.setId(id);
        missoes.add(missao);
        return id;
    }

    public List<Missao> listar() {
        return new ArrayList<>(missoes);
    }
}
