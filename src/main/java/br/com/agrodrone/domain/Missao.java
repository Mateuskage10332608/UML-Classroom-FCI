package br.com.agrodrone.domain;

import br.com.agrodrone.domain.enums.StatusMissao;
import br.com.agrodrone.domain.enums.StatusDrone;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Modela uma missão de monitoramento realizada por um drone sobre uma área agrícola
 * em uma data e hora específicas. Cada missão possui um status e pode registrar
 * dados coletados durante o voo.
 */
public class Missao {
    private Long id;
    private LocalDateTime dataHora;
    private StatusMissao status;
    private AreaAgricola area;
    private Drone drone;
    private Usuario operador;
    private List<DadosColetados> dados = new ArrayList<>();

    public Missao() {}

    public Missao(Long id, LocalDateTime dataHora, StatusMissao status,
                  AreaAgricola area, Drone drone, Usuario operador) {
        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
        this.area = area;
        this.drone = drone;
        this.operador = operador;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public AreaAgricola getArea() { return area; }
    public void setArea(AreaAgricola area) { this.area = area; }
    public Drone getDrone() { return drone; }
    public void setDrone(Drone drone) { this.drone = drone; }
    public Usuario getOperador() { return operador; }
    public void setOperador(Usuario operador) { this.operador = operador; }
    public List<DadosColetados> getDados() { return dados; }
    public void setDados(List<DadosColetados> dados) { this.dados = dados; }

    /**
     * Inicia a missão, alterando seu status para EM_ANDAMENTO e marcando o drone
     * como ocupado. Em sistemas mais complexos, esta rotina também poderia
     * verificar bateria, sensores ou outras condições de voo.
     */
    public void iniciarMissao() {
        this.status = StatusMissao.EM_ANDAMENTO;
        if (this.drone != null) {
            this.drone.setStatus(StatusDrone.EM_MISSAO);
        }
    }

    /**
     * Conclui a missão, alterando seu status para CONCLUIDA e liberando o drone.
     */
    public void concluirMissao() {
        this.status = StatusMissao.CONCLUIDA;
        if (this.drone != null) {
            this.drone.setStatus(StatusDrone.DISPONIVEL);
        }
    }

    /**
     * Registra um conjunto de dados coletados durante a missão.
     * @param dadosColetados dados coletados no voo
     */
    public void registrarDados(DadosColetados dadosColetados) {
        if (dadosColetados != null) {
            dadosColetados.setMissao(this);
            this.dados.add(dadosColetados);
        }
    }
}
