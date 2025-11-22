package br.com.agrodrone.model;

import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Sensor de umidade relativa.
 */
public class SensorUmidade extends Sensor {
    public SensorUmidade(String numeroSerial) {
        super(TipoSensor.UMIDADE, numeroSerial);
    }

    @Override
    public String ler() {
        return "50.0";
    }

    @Override
    public boolean autoVerificacao() {
        return true;
    }
}
