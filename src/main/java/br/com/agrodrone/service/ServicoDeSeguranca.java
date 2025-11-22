package br.com.agrodrone.service;

import br.com.agrodrone.domain.enums.Papel;

/**
 * Serviço responsável por validar tokens de acesso e verificar permissões.
 */
public class ServicoDeSeguranca {
    public boolean autorizar(String token, Papel papelEsperado) {
        if (token == null || token.trim().isEmpty()) {
            return false;
        }
        String[] partes = token.split(":");
        if (partes.length < 1) {
            return false;
        }
        try {
            Papel papel = Papel.valueOf(partes[0]);
            return papel == papelEsperado;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public String obterNomeDeUsuario(String token) {
        if (token == null) {
            return null;
        }
        String[] partes = token.split(":");
        return partes.length >= 2 ? partes[1] : null;
    }
}
