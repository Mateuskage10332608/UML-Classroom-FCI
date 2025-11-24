package br.com.agrodrone.model;

import java.time.LocalDateTime;
import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Representa uma leitura de sensor associada a uma missão.
 */
public class DadoSensor extends EntidadeBase {
    private Missao missao;
    private TipoSensor tipoSensor;
    private String valor;
    private LocalDateTime registradoEm;

    public DadoSensor(Missao missao,
                      TipoSensor tipoSensor,
                      String valor,
                      LocalDateTime registradoEm) {
        this.missao = missao;
        this.tipoSensor = tipoSensor;
        this.valor = valor;
        this.registradoEm = registradoEm;
    }

    public Missao getMissao() { return missao; }
    public void setMissao(Missao missao) { this.missao = missao; }
    public TipoSensor getTipoSensor() { return tipoSensor; }
    public void setTipoSensor(TipoSensor tipoSensor) { this.tipoSensor = tipoSensor; }
    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }
    public LocalDateTime getRegistradoEm() { return registradoEm; }
    public void setRegistradoEm(LocalDateTime registradoEm) { this.registradoEm = registradoEm; }
}
