package br.com.agrodrone.domain;

/**
 * Define uma área agrícola monitorada pela cooperativa.
 */
public class AreaAgricola {
    private int id;
    private String localizacao;
    private double tamanho;       // em hectares
    private String tipoCultivo;   // ex.: soja, milho

    public AreaAgricola() {}

    public AreaAgricola(int id, String localizacao, double tamanho, String tipoCultivo) {
        this.id = id;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        this.tipoCultivo = tipoCultivo;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public double getTamanho() { return tamanho; }
    public void setTamanho(double tamanho) { this.tamanho = tamanho; }
    public String getTipoCultivo() { return tipoCultivo; }
    public void setTipoCultivo(String tipoCultivo) { this.tipoCultivo = tipoCultivo; }
}
