package br.com.agrodrone.service;

import br.com.agrodrone.domain.enums.EstadoMissao;
import br.com.agrodrone.domain.enums.EventoMissao;

/**
 * Máquina de estados para controlar o ciclo de vida de uma missão.
 */
public class MaquinaEstadoMissao {
    private EstadoMissao estadoAtual = EstadoMissao.AGENDADA;

    public EstadoMissao getEstadoAtual() {
        return estadoAtual;
    }

    public void processarEvento(EventoMissao evento) {
        switch (estadoAtual) {
            case AGENDADA:
                if (evento == EventoMissao.HORARIO_INICIO_ALCANCADO) {
                    estadoAtual = EstadoMissao.EM_EXECUCAO;
                } else if (evento == EventoMissao.CANCELADA_PELO_USUARIO) {
                    estadoAtual = EstadoMissao.ABORTADA;
                }
                break;
            case EM_EXECUCAO:
                if (evento == EventoMissao.TAREFAS_CONCLUIDAS) {
                    estadoAtual = EstadoMissao.COMPLETA;
                } else if (evento == EventoMissao.ABORTO_MANUAL ||
                           evento == EventoMissao.FALHA_DRONE ||
                           evento == EventoMissao.TIMEOUT) {
                    estadoAtual = EstadoMissao.ABORTADA;
                }
                break;
            case ABORTADA:
                if (evento == EventoMissao.REAGENDAR) {
                    estadoAtual = EstadoMissao.AGENDADA;
                }
                break;
            default:
                break;
        }
    }
}
