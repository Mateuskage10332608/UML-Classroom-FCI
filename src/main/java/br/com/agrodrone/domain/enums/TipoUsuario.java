package br.com.agrodrone.domain.enums;

/**
 * Define os tipos de usuário que podem interagir com o sistema.
 */
public enum TipoUsuario {
    /** Usuário com permissões administrativas. */
    ADMINISTRADOR,
    /** Usuário responsável pelo cadastro de missões e coleta de dados. */
    OPERADOR;
}
