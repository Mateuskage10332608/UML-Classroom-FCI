package br.com.agrodrone.domain.enums;

/**
 * Eventos possíveis que podem ocorrer no ciclo de vida de um drone.
 */
public enum EventoDrone {
    MISSAO_RECEBIDA, DECOLAGEM, MISSAO_CONCLUIDA, BATERIA_BAIXA,
    FALHA_CRITICA, POUSO, DESLIGAMENTO_COMPLETO, MANUTENCAO_CONCLUIDA;
}
