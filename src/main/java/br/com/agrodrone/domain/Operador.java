package br.com.agrodrone.domain;

import java.util.List;
import java.util.ArrayList;

/**
 * Representa um operador de drone, responsável por agendar e gerenciar missões.
 * Herda de Usuario.
 */
public class Operador extends Usuario {

    public Operador() {
        super();
    }

    public Operador(int id, String nome, String login, String senhaHash) {
        super(id, nome, login, senhaHash);
    }

    /**
     * Método para agendar uma nova missão.
     * @param missao O objeto Missao a ser agendado.
     * @return true se a missão foi agendada com sucesso, false caso contrário.
     */
    public boolean agendarMissao(Missao missao) {
        // Lógica para agendar a missão (ex: persistir no banco de dados, validar)
        System.out.println("Operador " + this.getNome() + " agendou a missão ID: " + missao.getId());
        return true; // Simplificado
    }

    /**
     * Método para consultar as missões agendadas por este operador.
     * @return Uma lista de missões.
     */
    public List<Missao> consultarMissoes() {
        System.out.println("Operador " + this.getNome() + " consultando missões.");
        return new ArrayList<>(); // Retorna lista vazia para exemplo
    }
}
