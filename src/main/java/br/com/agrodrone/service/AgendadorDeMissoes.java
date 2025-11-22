package br.com.agrodrone.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.agrodrone.domain.AreaAgricola;
import br.com.agrodrone.domain.Checklist;
import br.com.agrodrone.domain.Drone;
import br.com.agrodrone.domain.Missao;
import br.com.agrodrone.domain.Operador;
import br.com.agrodrone.dto.MissaoDTO;
import br.com.agrodrone.repository.AreaRepositorio;
import br.com.agrodrone.repository.MissaoRepositorio;

/**
 * Serviço responsável por orquestrar o agendamento de missões.
 */
public class AgendadorDeMissoes {
    private final ServicoDeDrone servicoDeDrone;
    private final AreaRepositorio areaRepositorio;
    private final MissaoRepositorio missaoRepositorio;

    public AgendadorDeMissoes(ServicoDeDrone servicoDeDrone,
                              AreaRepositorio areaRepositorio,
                              MissaoRepositorio missaoRepositorio) {
        this.servicoDeDrone = servicoDeDrone;
        this.areaRepositorio = areaRepositorio;
        this.missaoRepositorio = missaoRepositorio;
    }

    public int agendarMissao(MissaoDTO dto, Operador operador) {
        Drone drone = servicoDeDrone.obterDrone(dto.getDroneId());
        if (drone == null) {
            throw new IllegalArgumentException("Drone não encontrado: " + dto.getDroneId());
        }
        AreaAgricola area = areaRepositorio.buscarPorId(dto.getAreaId());
        if (area == null) {
            throw new IllegalArgumentException("Área não encontrada: " + dto.getAreaId());
        }

        Checklist checklist = construirChecklistParaDrone(drone);
        if (!checklist.validar()) {
            throw new IllegalStateException("Checklist de pré-voo não aprovado");
        }

        LocalDateTime inicio = dto.getInicio();
        LocalDateTime fim = dto.getFim();
        List<Missao> sobreposicoes = missaoRepositorio.buscarSobreposicoes(drone.getId(), inicio, fim);
        if (!sobreposicoes.isEmpty()) {
            throw new IllegalStateException("Existe sobreposição de missão para o mesmo drone");
        }

        Missao missao = dto.toMissao(drone, area, operador, checklist);
        return missaoRepositorio.salvar(missao);
    }

    private Checklist construirChecklistParaDrone(Drone drone) {
        int bateria = 80;
        boolean sensoresOk = true;
        boolean conexaoOk = true;
        return new Checklist(0, bateria, sensoresOk, conexaoOk);
    }
}
