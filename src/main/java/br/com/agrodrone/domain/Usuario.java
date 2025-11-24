package br.com.agrodrone.domain;


/**
 * Representa um usuÃ¡rio do sistema. Esta classe Ã© abstrata e serve como base
 * para tipos especÃ­ficos de usuÃ¡rios como Administrador e Operador.
 */
public abstract class Usuario {
    protected int id;
    protected String nome;
    protected String login;
    protected String senhaHash;
    // O atributo 'tipo' foi removido pois a heranÃ§a jÃ¡ define o tipo de usuÃ¡rio.

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
     * MÃ©todo para o usuÃ¡rio tentar fazer login atravÃ©s de um Autenticador.
     * @param autenticador O serviÃ§o de autenticaÃ§Ã£o a ser usado.
     * @return true se o login for bem-sucedido, false caso contrÃ¡rio.
     */
    public boolean fazerLogin(Autenticador autenticador) {
        // A lÃ³gica de autenticaÃ§Ã£o real serÃ¡ implementada no Autenticador.
        // Aqui, apenas chamamos o mÃ©todo do autenticador.
        return autenticador.autenticar(this.login, this.senhaHash) != null; // Simplificado para exemplo
    }
}
