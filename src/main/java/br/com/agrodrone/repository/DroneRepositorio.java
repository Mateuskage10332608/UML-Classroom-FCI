package br.com.agrodrone.repository;

import java.util.HashMap;
import java.util.Map;

import br.com.agrodrone.domain.Drone;

/**
 * Repositório em memória para drones.
 */
public class DroneRepositorio {
    private final Map<Integer, Drone> banco = new HashMap<>();

    public Drone buscarPorId(int id) {
        return banco.get(id);
    }

    public void salvar(Drone drone) {
        banco.put(drone.getId(), drone);
    }
}
