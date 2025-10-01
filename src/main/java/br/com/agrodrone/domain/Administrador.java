package br.com.agrodrone.domain;

/**
 * Representa um usuário com permissões administrativas no sistema.
 * Herda de Usuario e pode realizar ações de gerenciamento.
 */
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(int id, String nome, String login, String senhaHash) {
        super(id, nome, login, senhaHash);
    }

    /**
     * Método para cadastrar um novo usuário no sistema.
     * @param novoUsuario O objeto Usuario a ser cadastrado.
     */
    public void cadastrarUsuario(Usuario novoUsuario) {
        // Lógica para cadastrar usuário (ex: persistir no banco de dados)
        System.out.println("Administrador " + this.getNome() + " cadastrou o usuário: " + novoUsuario.getNome());
    }

    /**
     * Método para realizar outras ações de gerenciamento do sistema.
     */
    public void gerenciarSistema() {
        System.out.println("Administrador " + this.getNome() + " está gerenciando o sistema.");
    }
}
