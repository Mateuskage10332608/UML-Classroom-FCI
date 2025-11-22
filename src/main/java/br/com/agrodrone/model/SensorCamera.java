package br.com.agrodrone.model;

import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Sensor de captura de imagem.
 */
public class SensorCamera extends Sensor {
    public SensorCamera(String numeroSerial) {
        super(TipoSensor.CAMERA, numeroSerial);
    }

    @Override
    public String ler() {
        return "<imagem_base64>";
    }

    @Override
    public boolean autoVerificacao() {
        return true;
    }
}
