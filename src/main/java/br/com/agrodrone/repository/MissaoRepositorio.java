package br.com.agrodrone.repository;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.agrodrone.domain.AreaAgricola;
import br.com.agrodrone.domain.Drone;
import br.com.agrodrone.domain.Missao;
import br.com.agrodrone.domain.enums.StatusMissao;

/**
 * Repositório em memória para missões.
 * Agora inclui persistência simples em arquivo texto (missoes.csv).
 */
public class MissaoRepositorio {
    private final List<Missao> missoes = new ArrayList<>();
    private int sequenciaId = 1;
    private static final String ARQUIVO = "missoes.csv";

    /**
     * Busca missões sobrepostas para um mesmo drone entre dois horários.
     */
    public List<Missao> buscarSobreposicoes(int droneId,
                                            LocalDateTime inicio,
                                            LocalDateTime fim) {
        List<Missao> sobrepostas = new ArrayList<>();
        for (Missao m : missoes) {
            if (m.getDrone() == null || m.getDrone().getId() != droneId) {
                continue;
            }
            LocalDateTime inicioExistente = m.getDataHora();
            if (inicioExistente == null) {
                continue;
            }
            // Regra simples: considera sobreposição se o horário de início da missão
            // existente cai dentro do intervalo [inicio, fim] da nova missão.
            boolean overlap = !fim.isBefore(inicioExistente) && !inicio.isAfter(inicioExistente);
            if (overlap) {
                sobrepostas.add(m);
            }
        }
        return sobrepostas;
    }

    /**
     * Salva uma nova missão, atribuindo um ID sequencial.
     */
    public synchronized int salvar(Missao missao) {
        int id = sequenciaId++;
        missao.setId(id);
        missoes.add(missao);
        return id;
    }

    /**
     * Lista uma cópia das missões em memória.
     */
    public List<Missao> listar() {
        return new ArrayList<>(missoes);
    }

    /**
     * Persiste as missões em um arquivo texto simples.
     * Formato: id;dataHoraISO;status;areaId;droneId
     */
    public void salvarEmDisco() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Missao m : missoes) {
                int id = m.getId();
                LocalDateTime dh = m.getDataHora();
                String dhStr = dh != null ? dh.toString() : "";
                String statusStr = m.getStatus() != null ? m.getStatus().name() : "";
                int areaId = m.getArea() != null ? m.getArea().getId() : -1;
                int droneId = m.getDrone() != null ? m.getDrone().getId() : -1;
                pw.println(id + ";" + dhStr + ";" + statusStr + ";" + areaId + ";" + droneId);
            }
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar missões em disco: " + e.getMessage());
        }
    }

    /**
     * Carrega missões de disco, reconstruindo referências para área e drone
     * a partir dos respectivos repositórios.
     */
    public void carregarDeDisco(AreaRepositorio areaRepo, DroneRepositorio droneRepo) {
        File f = new File(ARQUIVO);
        if (!f.exists()) {
            return;
        }
        List<Missao> carregadas = new ArrayList<>();
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                String[] partes = linha.split(";");
                if (partes.length < 5) continue;

                int id;
                try {
                    id = Integer.parseInt(partes[0]);
                } catch (NumberFormatException e) {
                    continue;
                }

                String dhStr = partes[1];
                LocalDateTime dataHora = dhStr.isEmpty() ? null : LocalDateTime.parse(dhStr);

                String statusStr = partes[2];
                StatusMissao status = statusStr.isEmpty() ? null : StatusMissao.valueOf(statusStr);

                int areaId;
                try {
                    areaId = Integer.parseInt(partes[3]);
                } catch (NumberFormatException e) {
                    areaId = -1;
                }

                int droneId;
                try {
                    droneId = Integer.parseInt(partes[4]);
                } catch (NumberFormatException e) {
                    droneId = -1;
                }

                Missao m = new Missao();
                m.setId(id);
                m.setDataHora(dataHora);
                m.setStatus(status);

                AreaAgricola area = areaRepo.buscarPorId(areaId);
                if (area != null) {
                    m.setArea(area);
                }

                Drone drone = droneRepo.buscarPorId(droneId);
                if (drone != null) {
                    m.setDrone(drone);
                }

                carregadas.add(m);
                if (id > maxId) {
                    maxId = id;
                }
            }
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível carregar missões de disco: " + e.getMessage());
        }
        missoes.clear();
        missoes.addAll(carregadas);
        sequenciaId = maxId + 1;
    }
}
