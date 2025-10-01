package br.com.agrodrone.domain;

import java.time.LocalDateTime;

/**
 * Representa um conjunto de dados coletados por um drone durante uma missão.
 * Pode incluir imagem (em bytes), temperatura, umidade, pragas identificadas
 * e a data/hora da coleta.
 */
public class DadosColetados {
    private int id;
    private byte[] imagem;
    private Double temperatura;
    private Double umidade;
    private String pragas;
    private LocalDateTime dataHora;
    // private Missao missao; // Removido, pois a associação é unidirecional de Missao para DadosColetados

    public DadosColetados() {}

    public DadosColetados(int id, byte[] imagem, Double temperatura, Double umidade,
                          String pragas, LocalDateTime dataHora /*, Missao missao*/) {
        this.id = id;
        this.imagem = imagem;
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.pragas = pragas;
        this.dataHora = dataHora;
        // this.missao = missao;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public byte[] getImagem() { return imagem; }
    public void setImagem(byte[] imagem) { this.imagem = imagem; }
    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }
    public Double getUmidade() { return umidade; }
    public void setUmidade(Double umidade) { this.umidade = umidade; }
    public String getPragas() { return pragas; }
    public void setPragas(String pragas) { this.pragas = pragas; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    // public Missao getMissao() { return missao; }
    // public void setMissao(Missao missao) { this.missao = missao; }
}
