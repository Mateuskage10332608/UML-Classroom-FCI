package br.com.agrodrone.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.agrodrone.domain.enums.EstadoDrone;
import br.com.agrodrone.domain.enums.TipoSensor;

/**
 * Representa um drone no modelo final.
 */
public class Drone extends EntidadeBase implements FornecedorDeSensores {
    private String codigoDrone;
    private EstadoDrone estado;
    private int bateriaPercentual;
    private final List<Sensor> sensores;

    public Drone(String codigoDrone, EstadoDrone estado, int bateriaPercentual) {
        this.codigoDrone = codigoDrone;
        this.estado = estado;
        this.bateriaPercentual = bateriaPercentual;
        this.sensores = new ArrayList<>();
    }

    public String getCodigoDrone() { return codigoDrone; }
    public void setCodigoDrone(String codigoDrone) { this.codigoDrone = codigoDrone; }
    public EstadoDrone getEstado() { return estado; }
    public void setEstado(EstadoDrone estado) { this.estado = estado; }
    public int getBateriaPercentual() { return bateriaPercentual; }
    public void setBateriaPercentual(int bateriaPercentual) { this.bateriaPercentual = bateriaPercentual; }

    public void adicionarSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }

    public boolean verificarPronto(int bateriaMinima) {
        if (this.bateriaPercentual < bateriaMinima) {
            return false;
        }
        for (Sensor s : sensores) {
            if (!s.autoVerificacao()) {
                return false;
            }
        }
        return true;
    }

    public void marcarManutencao(String motivo) {
        this.estado = EstadoDrone.MANUTENCAO;
    }

    @Override
    public List<TipoSensor> listarSensores() {
        List<TipoSensor> tipos = new ArrayList<>();
        for (Sensor s : sensores) {
            tipos.add(s.getTipo());
        }
        return Collections.unmodifiableList(tipos);
    }
}
