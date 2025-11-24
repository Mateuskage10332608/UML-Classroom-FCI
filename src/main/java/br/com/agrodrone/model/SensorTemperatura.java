package br.com.agrodrone.model;

import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Sensor de temperatura.
 */
public class SensorTemperatura extends Sensor {
    public SensorTemperatura(String numeroSerial) {
        super(TipoSensor.TEMPERATURA, numeroSerial);
    }

    @Override
    public String ler() {
        return "25.0";
    }

    @Override
    public boolean autoVerificacao() {
        return true;
    }
}
