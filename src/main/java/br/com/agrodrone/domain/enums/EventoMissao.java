package br.com.agrodrone.domain.enums;

/**
 * Eventos que alteram o estado de uma missão.
 */
public enum EventoMissao {
    HORARIO_INICIO_ALCANCADO, TAREFAS_CONCLUIDAS, ABORTO_MANUAL,
    FALHA_DRONE, TIMEOUT, CANCELADA_PELO_USUARIO, REAGENDAR;
}
