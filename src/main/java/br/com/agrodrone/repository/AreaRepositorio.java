package br.com.agrodrone.repository;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

import br.com.agrodrone.domain.AreaAgricola;

/**
 * Repositório em memória para áreas agrícolas.
 */
public class AreaRepositorio {
    private final Map<Integer, AreaAgricola> banco = new HashMap<>();
    private static final String ARQUIVO = "areas.ser";

    public AreaAgricola buscarPorId(int id) {
        return banco.get(id);
    }

    public void salvar(AreaAgricola area) {
        banco.put(area.getId(), area);
    }

    /**
     * Retorna todas as áreas cadastradas.
     */
    public Collection<AreaAgricola> listarTodas() {
        return banco.values();
    }

    /**
     * Persiste o conteúdo do repositório em disco.
     */
    public void salvarEmDisco() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(banco);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar áreas em disco: " + e.getMessage());
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
                banco.putAll((Map<Integer, AreaAgricola>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: não foi possível carregar áreas de disco: " + e.getMessage());
        }
    }
}
