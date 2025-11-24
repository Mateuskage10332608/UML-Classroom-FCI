package br.com.agrodrone.service;

import br.com.agrodrone.domain.Drone;
import br.com.agrodrone.repository.DroneRepositorio;

/**
 * Serviço dedicado a operações envolvendo drones.
 */
public class ServicoDeDrone {
    private final DroneRepositorio repositorio;

    public ServicoDeDrone(DroneRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Drone obterDrone(int droneId) {
        return repositorio.buscarPorId(droneId);
    }
}
