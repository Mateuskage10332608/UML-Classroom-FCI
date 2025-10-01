package br.com.agrodrone.domain;

import br.com.agrodrone.domain.enums.TipoUsuario;

/**
 * Representa um usuário do sistema. Esta classe é abstrata e serve como base
 * para tipos específicos de usuários como Administrador e Operador.
 */
public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String login;
    protected String senhaHash;
    // O atributo 'tipo' foi removido pois a herança já define o tipo de usuário.

    public Usuario() {}

    public Usuario(int id, String nome, String login, String senhaHash) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senhaHash = senhaHash;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }

    /**
     * Método para o usuário tentar fazer login através de um Autenticador.
     * @param autenticador O serviço de autenticação a ser usado.
     * @return true se o login for bem-sucedido, false caso contrário.
     */
    public boolean fazerLogin(Autenticador autenticador) {
        // A lógica de autenticação real será implementada no Autenticador.
        // Aqui, apenas chamamos o método do autenticador.
        return autenticador.autenticar(this.login, this.senhaHash) != null; // Simplificado para exemplo
    }
}
