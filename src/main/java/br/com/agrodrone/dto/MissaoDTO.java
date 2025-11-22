package br.com.agrodrone.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.com.agrodrone.domain.AreaAgricola;
import br.com.agrodrone.domain.Drone;
import br.com.agrodrone.domain.Missao;
import br.com.agrodrone.domain.Operador;
import br.com.agrodrone.domain.Checklist;
import br.com.agrodrone.domain.enums.StatusMissao;

/**
 * DTO (Data Transfer Object) para agendamento de missões.
 */
public class MissaoDTO {
    private String nome;
    private int droneId;
    private int areaId;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<String> sensoresSolicitados;

    public MissaoDTO() { }

    public MissaoDTO(String nome, int droneId, int areaId, LocalDateTime inicio,
                     LocalDateTime fim, List<String> sensoresSolicitados) {
        this.nome = nome;
        this.droneId = droneId;
        this.areaId = areaId;
        this.inicio = inicio;
        this.fim = fim;
        this.sensoresSolicitados = sensoresSolicitados;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getDroneId() { return droneId; }
    public void setDroneId(int droneId) { this.droneId = droneId; }
    public int getAreaId() { return areaId; }
    public void setAreaId(int areaId) { this.areaId = areaId; }
    public LocalDateTime getInicio() { return inicio; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public LocalDateTime getFim() { return fim; }
    public void setFim(LocalDateTime fim) { this.fim = fim; }
    public List<String> getSensoresSolicitados() { return sensoresSolicitados; }
    public void setSensoresSolicitados(List<String> sensoresSolicitados) { this.sensoresSolicitados = sensoresSolicitados; }

    public Missao toMissao(Drone drone, AreaAgricola area,
                           Operador operador, Checklist checklist) {
        Missao missao = new Missao();
        missao.setId(0);
        missao.setDataHora(this.inicio);
        missao.setStatus(StatusMissao.AGENDADA);
        missao.setArea(area);
        missao.setDrone(drone);
        missao.setOperador(operador);
        missao.setChecklist(checklist);
        return missao;
    }
}
