package br.com.agrodrone.model;

/**
 * Representa uma área agrícola monitorada.
 */
public class Area extends EntidadeBase {
    private String nome;
    private double tamanhoHectares;
    private String localizacaoGeoJson;
    private String tipoCultivo;

    public Area(String nome, double tamanhoHectares,
                String localizacaoGeoJson, String tipoCultivo) {
        this.nome = nome;
        this.tamanhoHectares = tamanhoHectares;
        this.localizacaoGeoJson = localizacaoGeoJson;
        this.tipoCultivo = tipoCultivo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getTamanhoHectares() { return tamanhoHectares; }
    public void setTamanhoHectares(double tamanhoHectares) { this.tamanhoHectares = tamanhoHectares; }
    public String getLocalizacaoGeoJson() { return localizacaoGeoJson; }
    public void setLocalizacaoGeoJson(String localizacaoGeoJson) { this.localizacaoGeoJson = localizacaoGeoJson; }
    public String getTipoCultivo() { return tipoCultivo; }
    public void setTipoCultivo(String tipoCultivo) { this.tipoCultivo = tipoCultivo; }

    public double calcularCobertura() {
        return this.tamanhoHectares;
    }
}
