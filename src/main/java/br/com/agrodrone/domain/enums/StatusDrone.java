package br.com.agrodrone.domain.enums;

/**
 * Estados possíveis para o drone. São usados para verificar disponibilidade
 * antes de agendar missões e durante a execução das mesmas.
 */
public enum StatusDrone {
    /** Drone disponível para missões. */
    DISPONIVEL,
    /** Drone atualmente em missão. */
    EM_MISSAO,
    /** Drone fora de operação devido à manutenção. */
    EM_MANUTENCAO;
}
