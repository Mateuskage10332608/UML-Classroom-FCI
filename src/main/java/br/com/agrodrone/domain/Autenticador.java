package br.com.agrodrone.domain;

/**
 * Classe responsável por gerenciar a lógica de autenticação de usuários.
 * Separa a responsabilidade de autenticação da classe Usuario.
 */
public class Autenticador {

    /**
     * Tenta autenticar um usuário com base no login e senha (hash).
     * Em um sistema real, esta lógica acessaria um banco de dados ou serviço de autenticação.
     * @param login O login fornecido pelo usuário.
     * @param senhaHash O hash da senha fornecida pelo usuário.
     * @return O objeto Usuario autenticado se as credenciais forem válidas, ou null caso contrário.
     */
    public Usuario autenticar(String login, String senhaHash) {
        System.out.println("Tentando autenticar usuário: " + login);
        // Lógica de autenticação real (ex: buscar no banco, comparar hashes)
        // Para este exemplo, vamos simular um usuário válido
        if ("operador1".equals(login) && "hashsenha123".equals(senhaHash)) {
            return new Operador(1, "Operador Teste", "operador1", "hashsenha123");
        } else if ("admin1".equals(login) && "hashadmin123".equals(senhaHash)) {
            return new Administrador(2, "Admin Teste", "admin1", "hashadmin123");
        }
        return null; // Autenticação falhou
    }

    /**
     * Gera um token de sessão para um usuário autenticado.
     * Em um sistema real, isso envolveria JWT ou outros mecanismos de segurança.
     * @param usuario O usuário autenticado.
     * @return Um token de sessão (String).
     */
    public String gerarTokenSessao(Usuario usuario) {
        if (usuario != null) {
            return "TOKEN_SESSAO_" + usuario.getId() + "_" + System.currentTimeMillis();
        }
        return null;
    }
}
