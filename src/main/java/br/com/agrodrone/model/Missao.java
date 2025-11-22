package br.com.agrodrone.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.agrodrone.domain.enums.EstadoMissao;
import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Representa uma missão de voo no modelo final.
 */
public class Missao extends EntidadeBase {
    private String nome;
    private Drone drone;
    private Area area;
    private LocalDateTime inicioAgendado;
    private LocalDateTime fimAgendado;
    private List<TipoSensor> sensoresSolicitados;
    private EstadoMissao estado;
    private Usuario criadaPor;

    public Missao() {
        this.sensoresSolicitados = new ArrayList<>();
        this.estado = EstadoMissao.AGENDADA;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Drone getDrone() { return drone; }
    public void setDrone(Drone drone) { this.drone = drone; }
    public Area getArea() { return area; }
    public void setArea(Area area) { this.area = area; }
    public LocalDateTime getInicioAgendado() { return inicioAgendado; }
    public void setInicioAgendado(LocalDateTime inicioAgendado) { this.inicioAgendado = inicioAgendado; }
    public LocalDateTime getFimAgendado() { return fimAgendado; }
    public void setFimAgendado(LocalDateTime fimAgendado) { this.fimAgendado = fimAgendado; }
    public List<TipoSensor> getSensoresSolicitados() { return sensoresSolicitados; }
    public void setSensoresSolicitados(List<TipoSensor> sensoresSolicitados) { this.sensoresSolicitados = sensoresSolicitados; }
    public EstadoMissao getEstado() { return estado; }
    public void setEstado(EstadoMissao estado) { this.estado = estado; }
    public Usuario getCriadaPor() { return criadaPor; }
    public void setCriadaPor(Usuario criadaPor) { this.criadaPor = criadaPor; }

    public boolean validar() {
        if (drone == null || area == null ||
            inicioAgendado == null || fimAgendado == null) {
            return false;
        }
        return inicioAgendado.isBefore(fimAgendado);
    }

    public boolean sobrepoeCom(Missao outra) {
        if (outra == null ||
            outra.getDrone() == null ||
            this.getDrone() == null) {
            return false;
        }
        if (!outra.getDrone().getCodigoDrone()
                 .equals(this.getDrone().getCodigoDrone())) {
            return false;
        }
        return this.inicioAgendado.isBefore(outra.getFimAgendado())
               && this.fimAgendado.isAfter(outra.getInicioAgendado());
    }
}
