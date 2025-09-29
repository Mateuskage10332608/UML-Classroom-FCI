package br.com.agrodrone.domain;

/**
 * Define uma área agrícola monitorada pela cooperativa.
 */
public class AreaAgricola {
    private Long id;
    private String localizacao;
    private Double tamanho;       // em hectares
    private String tipoCultivo;   // ex.: soja, milho

    public AreaAgricola() {}

    public AreaAgricola(Long id, String localizacao, Double tamanho, String tipoCultivo) {
        this.id = id;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        this.tipoCultivo = tipoCultivo;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public Double getTamanho() { return tamanho; }
    public void setTamanho(Double tamanho) { this.tamanho = tamanho; }
    public String getTipoCultivo() { return tipoCultivo; }
    public void setTipoCultivo(String tipoCultivo) { this.tipoCultivo = tipoCultivo; }
}
