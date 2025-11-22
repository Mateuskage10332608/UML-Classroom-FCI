package br.com.agrodrone.service;

import br.com.agrodrone.domain.enums.EstadoDrone;
import br.com.agrodrone.domain.enums.EventoDrone;

/**
 * Máquina de estados para o ciclo de vida do drone.
 */
public class MaquinaEstadoDrone {
    private EstadoDrone estadoAtual = EstadoDrone.OCIOSO;
    private int bateriaPercentual;
    private boolean sensoresOk;
    private final int bateriaMinima;

    public MaquinaEstadoDrone(int bateriaPercentual,
                              boolean sensoresOk,
                              int bateriaMinima) {
        this.bateriaPercentual = bateriaPercentual;
        this.sensoresOk = sensoresOk;
        this.bateriaMinima = bateriaMinima;
    }

    public EstadoDrone getEstadoAtual() {
        return estadoAtual;
    }

    public void processarEvento(EventoDrone evento) {
        switch (estadoAtual) {
            case OCIOSO:
                if (evento == EventoDrone.MISSAO_RECEBIDA &&
                    bateriaPercentual >= bateriaMinima &&
                    sensoresOk) {
                    estadoAtual = EstadoDrone.PREPARANDO;
                }
                break;
            case PREPARANDO:
                if (evento == EventoDrone.DECOLAGEM) {
                    estadoAtual = EstadoDrone.EM_VOO;
                }
                break;
            case EM_VOO:
                if (evento == EventoDrone.MISSAO_CONCLUIDA ||
                    evento == EventoDrone.BATERIA_BAIXA) {
                    estadoAtual = EstadoDrone.RETORNANDO;
                } else if (evento == EventoDrone.FALHA_CRITICA) {
                    estadoAtual = EstadoDrone.MANUTENCAO;
                }
                break;
            case RETORNANDO:
                if (evento == EventoDrone.POUSO) {
                    estadoAtual = EstadoDrone.POUSADO;
                }
                break;
            case POUSADO:
                if (evento == EventoDrone.DESLIGAMENTO_COMPLETO) {
                    estadoAtual = EstadoDrone.OCIOSO;
                }
                break;
            case MANUTENCAO:
                if (evento == EventoDrone.MANUTENCAO_CONCLUIDA) {
                    estadoAtual = EstadoDrone.OCIOSO;
                }
                break;
            default:
                break;
        }
    }

    public void setBateriaPercentual(int bateriaPercentual) {
        this.bateriaPercentual = bateriaPercentual;
    }

    public void setSensoresOk(boolean sensoresOk) {
        this.sensoresOk = sensoresOk;
    }
}
