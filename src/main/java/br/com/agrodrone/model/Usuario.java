package br.com.agrodrone.model;

import br.com.agrodrone.domain.enums.Papel;

/**
 * Representa um usuário do sistema no modelo final.
 */
public class Usuario extends EntidadeBase implements Autorizavel {
    private String nomeUsuario;
    private String hashSenha;
    private Papel papel;

    public Usuario(String nomeUsuario,
                   String hashSenha,
                   Papel papel) {
        this.nomeUsuario = nomeUsuario;
        this.hashSenha = hashSenha;
        this.papel = papel;
    }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public String getHashSenha() { return hashSenha; }
    public void setHashSenha(String hashSenha) { this.hashSenha = hashSenha; }
    public Papel getPapel() { return papel; }
    public void setPapel(Papel papel) { this.papel = papel; }

    @Override
    public boolean possuiPapel(Papel papel) {
        return this.papel == papel;
    }
}
