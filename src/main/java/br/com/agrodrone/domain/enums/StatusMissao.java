package br.com.agrodrone.domain.enums;

/**
 * Representa os possíveis estados de uma missão de voo.
 */
public enum StatusMissao {
    /** Missão agendada, ainda não iniciada. */
    AGENDADA,
    /** Missão em andamento, drone em voo. */
    EM_ANDAMENTO,
    /** Missão concluída com sucesso. */
    CONCLUIDA,
    /** Missão cancelada antes ou durante a execução. */
    CANCELADA;
}
