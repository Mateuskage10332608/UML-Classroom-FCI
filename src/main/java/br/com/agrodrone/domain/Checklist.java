package br.com.agrodrone.domain;

import java.time.LocalDateTime;

/**
 * Representa um checklist de pré-voo para uma missão de drone.
 * Contém itens de verificação como nível de bateria, status dos sensores e conexão de rede.
 */
public class Checklist {
    private int id;
    private int nivelBateria;
    private boolean sensoresOk;
    private boolean conexaoRedeOk;
    private LocalDateTime dataValidacao;

    public Checklist() {}

    public Checklist(int id, int nivelBateria, boolean sensoresOk, boolean conexaoRedeOk) {
        this.id = id;
        this.nivelBateria = nivelBateria;
        this.sensoresOk = sensoresOk;
        this.conexaoRedeOk = conexaoRedeOk;
        this.dataValidacao = LocalDateTime.now(); // Data de validação é a data de criação do checklist
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getNivelBateria() { return nivelBateria; }
    public void setNivelBateria(int nivelBateria) { this.nivelBateria = nivelBateria; }
    public boolean isSensoresOk() { return sensoresOk; }
    public void setSensoresOk(boolean sensoresOk) { this.sensoresOk = sensoresOk; }
    public boolean isConexaoRedeOk() { return conexaoRedeOk; }
    public void setConexaoRedeOk(boolean conexaoRedeOk) { this.conexaoRedeOk = conexaoRedeOk; }
    public LocalDateTime getDataValidacao() { return dataValidacao; }
    public void setDataValidacao(LocalDateTime dataValidacao) { this.dataValidacao = dataValidacao; }

    /**
     * Realiza a validação do checklist. Todos os itens devem estar OK para ser válido.
     * @return true se o checklist for válido, false caso contrário.
     */
    public boolean validar() {
        // Lógica de validação: bateria mínima, sensores ok, conexão ok
        return nivelBateria >= 70 && sensoresOk && conexaoRedeOk;
    }
}
