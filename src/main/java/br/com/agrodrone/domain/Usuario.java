package br.com.agrodrone.domain;

import br.com.agrodrone.domain.enums.TipoUsuario;

/**
 * Representa um usuário do sistema. Cada usuário possui um tipo (administrador
 * ou operador) e pode autenticar-se via login e senha (armazenada como hash).
 */
public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senhaHash;
    private TipoUsuario tipo;

    public Usuario() {}

    public Usuario(Long id, String nome, String login, String senhaHash, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senhaHash = senhaHash;
        this.tipo = tipo;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }

    /**
     * Método simplificado de autenticação para fins educacionais.
     * @param login fornecido
     * @param senha fornecida (em texto simples — a aplicação real deve usar hash e salt)
     * @return true se o login e a senha coincidirem com os dados do usuário
     */
    public boolean autenticar(String login, String senha) {
        return this.login != null && this.login.equals(login) &&
               this.senhaHash != null && this.senhaHash.equals(senha);
    }
}
