package br.com.agrodrone.controller;

import br.com.agrodrone.domain.Operador;
import br.com.agrodrone.domain.enums.Papel;
import br.com.agrodrone.dto.MissaoDTO;
import br.com.agrodrone.service.AgendadorDeMissoes;
import br.com.agrodrone.service.ServicoDeSeguranca;

/**
 * Controlador responsável por receber requisições de missões.
 */
public class ControladorMissao {
    private final ServicoDeSeguranca seguranca;
    private final AgendadorDeMissoes agendador;

    public ControladorMissao(ServicoDeSeguranca seguranca, AgendadorDeMissoes agendador) {
        this.seguranca = seguranca;
        this.agendador = agendador;
    }

    public int criarMissao(MissaoDTO dto, String token) {
        boolean autorizado = seguranca.autorizar(token, Papel.OPERADOR);
        if (!autorizado) {
            throw new SecurityException("Token inválido ou papel insuficiente");
        }
        String nomeUsuario = seguranca.obterNomeDeUsuario(token);
        Operador operador = new Operador(1, nomeUsuario, nomeUsuario, "hashplaceholder");
        return agendador.agendarMissao(dto, operador);
    }
}
