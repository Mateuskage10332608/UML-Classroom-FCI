package br.com.agrodrone.domain;

import br.com.agrodrone.domain.enums.StatusDrone;

/**
 * Representa um drone utilizado nas missões de monitoramento.
 */
public class Drone {
    private int id;
    private String modelo;
    private StatusDrone status;

    public Drone() {}

    public Drone(int id, String modelo, StatusDrone status) {
        this.id = id;
        this.modelo = modelo;
        this.status = status;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public StatusDrone getStatus() { return status; }
    public void setStatus(StatusDrone status) { this.status = status; }

    /**
     * Método de utilidade para obter o status atual do drone. Para sistemas mais
     * complexos, pode incluir verificação de diagnóstico.
     */
    public StatusDrone verificarStatus() { return this.status; }

    /**
     * Atualiza o status do drone.
     * @param novoStatus O novo status do drone.
     */
    public void atualizarStatus(StatusDrone novoStatus) {
        this.status = novoStatus;
    }
}
