package br.com.agrodrone.model;

import java.util.List;
import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Interface para objetos capazes de fornecer a lista de sensores suportados.
 */
public interface FornecedorDeSensores {
    List<TipoSensor> listarSensores();
}
