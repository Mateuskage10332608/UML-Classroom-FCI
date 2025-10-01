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
    private int id;
    private LocalDateTime dataHora;
    private StatusMissao status;
    private AreaAgricola area;
    private Drone drone;
    private Operador operador; // Agora associado a um Operador específico
    private Checklist checklist; // Nova associação com Checklist
    private List<DadosColetados> dados = new ArrayList<>();

    public Missao() {}

    public Missao(int id, LocalDateTime dataHora, StatusMissao status,
                  AreaAgricola area, Drone drone, Operador operador, Checklist checklist) {
        this.id = id;
        this.dataHora = dataHora;
        this.status = status;
        this.area = area;
        this.drone = drone;
        this.operador = operador;
        this.checklist = checklist;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public StatusMissao getStatus() { return status; }
    public void setStatus(StatusMissao status) { this.status = status; }
    public AreaAgricola getArea() { return area; }
    public void setArea(AreaAgricola area) { this.area = area; }
    public Drone getDrone() { return drone; }
    public void setDrone(Drone drone) { this.drone = drone; }
    public Operador getOperador() { return operador; }
    public void setOperador(Operador operador) { this.operador = operador; }
    public Checklist getChecklist() { return checklist; }
    public void setChecklist(Checklist checklist) { this.checklist = checklist; }
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
            this.drone.atualizarStatus(StatusDrone.EM_MISSAO);
        }
    }

    /**
     * Conclui a missão, alterando seu status para CONCLUIDA e liberando o drone.
     */
    public void concluirMissao() {
        this.status = StatusMissao.CONCLUIDA;
        if (this.drone != null) {
            this.drone.atualizarStatus(StatusDrone.DISPONIVEL);
        }
    }

    /**
     * Registra um conjunto de dados coletados durante a missão.
     * @param dadosColetados dados coletados no voo
     */
    public void adicionarDados(DadosColetados dadosColetados) {
        if (dadosColetados != null) {
            // dadosColetados.setMissao(this); // Remover se Missao não for um atributo em DadosColetados
            this.dados.add(dadosColetados);
        }
    }
}
