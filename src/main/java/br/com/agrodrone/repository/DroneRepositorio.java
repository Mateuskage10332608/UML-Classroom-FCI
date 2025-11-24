package br.com.agrodrone.repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import br.com.agrodrone.domain.Drone;

/**
 * Repositório em memória para drones.
 */
public class DroneRepositorio {
    private final Map<Integer, Drone> banco = new HashMap<>();
    private static final String ARQUIVO = "drones.ser";

    public Drone buscarPorId(int id) {
        return banco.get(id);
    }

    public void salvar(Drone drone) {
        banco.put(drone.getId(), drone);
    }

    /**
     * Retorna todos os drones cadastrados.
     */
    public Collection<Drone> listarTodos() {
        return banco.values();
    }

    /**
     * Persiste o conteúdo do repositório em disco.
     */
    public void salvarEmDisco() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(banco);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar drones em disco: " + e.getMessage());
        }
    }

    /**
     * Carrega o conteúdo do repositório a partir do disco, se existir.
     */
    @SuppressWarnings("unchecked")
    public void carregarDeDisco() {
        File f = new File(ARQUIVO);
        if (!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                banco.clear();
                banco.putAll((Map<Integer, Drone>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: não foi possível carregar drones de disco: " + e.getMessage());
        }
    }
}
