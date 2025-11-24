package br.com.agrodrone.model;

import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Classe abstrata que representa um sensor instalado em um drone.
 */
public abstract class Sensor extends EntidadeBase {
    private TipoSensor tipo;
    private String numeroSerial;

    protected Sensor(TipoSensor tipo, String numeroSerial) {
        this.tipo = tipo;
        this.numeroSerial = numeroSerial;
    }

    public TipoSensor getTipo() {
        return tipo;
    }

    public String getNumeroSerial() {
        return numeroSerial;
    }

    public abstract String ler();
    public abstract boolean autoVerificacao();
}
