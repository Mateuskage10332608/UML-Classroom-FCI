#!/usr/bin/env bash
set -euo pipefail

echo "=> Indo para a raiz do projeto..."
cd "$(dirname "$0")"

echo "=> Removendo BOM UTF-8 dos arquivos .java (aquele \\ufeff chato)..."
find src/main/java -name '*.java' -print0 | xargs -0 sed -i '1s/^\xEF\xBB\xBF//'

###############################################################################
# 1) AreaRepositorio.java - listarTodas() + salvarEmDisco()/carregarDeDisco()
###############################################################################
echo "=> Atualizando AreaRepositorio.java..."
cat > src/main/java/br/com/agrodrone/repository/AreaRepositorio.java <<'EOF'
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
EOF

###############################################################################
# 2) DroneRepositorio.java - listarTodos() + salvarEmDisco()/carregarDeDisco()
###############################################################################
echo "=> Atualizando DroneRepositorio.java..."
cat > src/main/java/br/com/agrodrone/repository/DroneRepositorio.java <<'EOF'
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
EOF

###############################################################################
# 3) AreaAgricola.java - torna serializável (mantém campos/métodos)
###############################################################################
echo "=> Atualizando AreaAgricola.java (implements Serializable)..."
cat > src/main/java/br/com/agrodrone/domain/AreaAgricola.java <<'EOF'
package br.com.agrodrone.domain;

import java.io.Serializable;

/**
 * Define uma área agrícola monitorada pela cooperativa.
 */
public class AreaAgricola implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String localizacao;
    private double tamanho;       // em hectares
    private String tipoCultivo;   // ex.: soja, milho

    public AreaAgricola() {}

    public AreaAgricola(int id, String localizacao, double tamanho, String tipoCultivo) {
        this.id = id;
        this.localizacao = localizacao;
        this.tamanho = tamanho;
        this.tipoCultivo = tipoCultivo;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public double getTamanho() { return tamanho; }
    public void setTamanho(double tamanho) { this.tamanho = tamanho; }
    public String getTipoCultivo() { return tipoCultivo; }
    public void setTipoCultivo(String tipoCultivo) { this.tipoCultivo = tipoCultivo; }
}
EOF

###############################################################################
# 4) Drone.java - torna serializável sem perder métodos existentes
###############################################################################
echo "=> Ajustando Drone.java para implementar Serializable..."
DRONE_FILE="src/main/java/br/com/agrodrone/domain/Drone.java"
if ! grep -q "implements java.io.Serializable" "$DRONE_FILE"; then
  # Adiciona implements e serialVersionUID preservando o resto da classe
  sed -i 's/public class Drone {/public class Drone implements java.io.Serializable {\n    private static final long serialVersionUID = 1L;/' "$DRONE_FILE"
fi

###############################################################################
# 5) MissaoRepositorio.java - reimplementa com sobreposição + persistência
###############################################################################
echo "=> Atualizando MissaoRepositorio.java (com persistência)..."
cat > src/main/java/br/com/agrodrone/repository/MissaoRepositorio.java <<'EOF'
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
EOF

###############################################################################
# 6) Main.java - menu completo + Trocar usuário + persistência
###############################################################################
echo "=> Atualizando Main.java..."
cat > src/main/java/br/com/agrodrone/Main.java <<'EOF'
package br.com.agrodrone;

import br.com.agrodrone.domain.*;
import br.com.agrodrone.domain.enums.StatusDrone;
import br.com.agrodrone.dto.MissaoDTO;
import br.com.agrodrone.repository.AreaRepositorio;
import br.com.agrodrone.repository.DroneRepositorio;
import br.com.agrodrone.repository.MissaoRepositorio;
import br.com.agrodrone.service.AgendadorDeMissoes;
import br.com.agrodrone.service.ServicoDeDrone;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Classe de entrada da aplicação de monitoramento agrícola.
 */
public class Main {
    private final AreaRepositorio areaRepo = new AreaRepositorio();
    private final DroneRepositorio droneRepo = new DroneRepositorio();
    private final MissaoRepositorio missaoRepo = new MissaoRepositorio();
    private final ServicoDeDrone servicoDeDrone = new ServicoDeDrone(droneRepo);
    private final AgendadorDeMissoes agendador = new AgendadorDeMissoes(servicoDeDrone, areaRepo, missaoRepo);

    private static final String ARQUIVO_SENSORES = "sensores_drone.ser";

    /**
     * Mapa auxiliar: id do drone -> lista de sensores disponíveis.
     */
    private final Map<Integer, List<String>> sensoresDoDrone = new HashMap<>();

    private final Scanner scanner = new Scanner(System.in);

    public Main() {
        // Carrega dados persistidos
        areaRepo.carregarDeDisco();
        droneRepo.carregarDeDisco();
        missaoRepo.carregarDeDisco(areaRepo, droneRepo);
        carregarSensoresDeDisco();
    }

    public static void main(String[] args) {
        new Main().executar();
    }

    private void executar() {
        System.out.println("Bem-vindo ao Sistema de Monitoramento de Plantações");
        Autenticador autenticador = new Autenticador();
        Usuario usuario = autenticarUsuario(autenticador);
        boolean sair = false;

        while (!sair) {
            try {
                if (usuario instanceof Administrador) {
                    int acao = menuAdministrador((Administrador) usuario);
                    if (acao == 0) {
                        sair = true;
                    } else if (acao == 2) {
                        usuario = autenticarUsuario(autenticador);
                    }
                } else if (usuario instanceof Operador) {
                    int acao = menuOperador((Operador) usuario);
                    if (acao == 0) {
                        sair = true;
                    } else if (acao == 2) {
                        usuario = autenticarUsuario(autenticador);
                    }
                } else {
                    System.out.println("Perfil não reconhecido. Encerrando sessão.");
                    sair = true;
                }
            } catch (Exception ex) {
                System.out.println("Erro: " + ex.getMessage());
            }
        }

        // Persistência ao encerrar
        areaRepo.salvarEmDisco();
        droneRepo.salvarEmDisco();
        missaoRepo.salvarEmDisco();
        salvarSensoresEmDisco();

        System.out.println("Sessão encerrada. Até logo!");
    }

    private Usuario autenticarUsuario(Autenticador autenticador) {
        Usuario usuario = null;
        while (usuario == null) {
            System.out.print("Login: ");
            String login = scanner.nextLine().trim();
            System.out.print("Senha (hash simulada): ");
            String senha = scanner.nextLine().trim();
            usuario = autenticador.autenticar(login, senha);
            if (usuario == null) {
                System.out.println("Credenciais inválidas. Tente novamente.");
            }
        }
        System.out.println("Usuário autenticado como " + (usuario instanceof Administrador ? "Administrador" : "Operador"));
        return usuario;
    }

    private int menuAdministrador(Administrador admin) {
        System.out.println("\n==== Menu Administrador ====");
        System.out.println("1. Cadastrar área agrícola");
        System.out.println("2. Cadastrar drone");
        System.out.println("3. Listar áreas");
        System.out.println("4. Listar drones");
        System.out.println("5. Trocar usuário");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine().trim();
        switch (opcao) {
            case "1":
                cadastrarArea();
                return 1;
            case "2":
                cadastrarDrone();
                return 1;
            case "3":
                listarAreas();
                return 1;
            case "4":
                listarDrones();
                return 1;
            case "5":
                return 2; // trocar usuário
            case "0":
                return 0; // sair
            default:
                System.out.println("Opção inválida.");
                return 1;
        }
    }

    private int menuOperador(Operador operador) {
        System.out.println("\n==== Menu Operador ====");
        System.out.println("1. Agendar missão");
        System.out.println("2. Registrar dados de missão");
        System.out.println("3. Gerar relatório");
        System.out.println("4. Listar missões");
        System.out.println("5. Trocar usuário");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
        String opcao = scanner.nextLine().trim();
        switch (opcao) {
            case "1":
                agendarMissao(operador);
                return 1;
            case "2":
                registrarDados();
                return 1;
            case "3":
                gerarRelatorio();
                return 1;
            case "4":
                listarMissoes();
                return 1;
            case "5":
                return 2; // trocar usuário
            case "0":
                return 0; // sair
            default:
                System.out.println("Opção inválida.");
                return 1;
        }
    }

    private void cadastrarArea() {
        System.out.print("ID da área: ");
        int id = lerInteiro();
        System.out.print("Localização: ");
        String localizacao = scanner.nextLine().trim();
        System.out.print("Tamanho (hectares): ");
        double tamanho = lerDouble();
        System.out.print("Tipo de cultivo: ");
        String tipoCultivo = scanner.nextLine().trim();
        AreaAgricola area = new AreaAgricola(id, localizacao, tamanho, tipoCultivo);
        areaRepo.salvar(area);
        System.out.println("Área cadastrada com sucesso.");
    }

    private void cadastrarDrone() {
        System.out.print("ID do drone: ");
        int id = lerInteiro();
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine().trim();
        StatusDrone status = escolherStatusDrone();
        Drone drone = new Drone(id, modelo, status);
        droneRepo.salvar(drone);
        System.out.print("Informe os sensores disponíveis separados por vírgula (ex.: CAMERA,TEMPERATURA,UMIDADE,NDVI): ");
        String sensoresStr = scanner.nextLine().trim();
        List<String> sensores = new ArrayList<>();
        if (!sensoresStr.isEmpty()) {
            String[] partes = sensoresStr.split(",");
            for (String s : partes) {
                sensores.add(s.trim().toUpperCase());
            }
        }
        sensoresDoDrone.put(id, sensores);
        System.out.println("Drone cadastrado com sucesso.");
    }

    private void agendarMissao(Operador operador) {
        System.out.print("Nome da missão: ");
        String nome = scanner.nextLine().trim();
        System.out.print("ID do drone: ");
        int droneId = lerInteiro();
        Drone drone = servicoDeDrone.obterDrone(droneId);
        if (drone == null) {
            System.out.println("Drone não encontrado.");
            return;
        }
        if (drone.getStatus() != StatusDrone.DISPONIVEL) {
            System.out.println("Drone não está disponível (status atual: " + drone.getStatus() + ").");
            return;
        }
        System.out.print("ID da área: ");
        int areaId = lerInteiro();
        if (areaRepo.buscarPorId(areaId) == null) {
            System.out.println("Área não encontrada.");
            return;
        }
        System.out.print("Data/hora de início (formato dd/MM/yyyy HH:mm): ");
        LocalDateTime inicio = lerDataHora();
        System.out.print("Data/hora de término (formato dd/MM/yyyy HH:mm): ");
        LocalDateTime fim = lerDataHora();
        if (!fim.isAfter(inicio)) {
            System.out.println("O horário de término deve ser posterior ao de início.");
            return;
        }
        List<String> sensoresDisponiveis = sensoresDoDrone.getOrDefault(droneId, Collections.emptyList());
        System.out.println("Sensores disponíveis para o drone: " + sensoresDisponiveis);
        System.out.print("Sensores a utilizar na missão (separe por vírgula): ");
        String sensoresStr = scanner.nextLine().trim();
        List<String> sensoresSolicitados = new ArrayList<>();
        if (!sensoresStr.isEmpty()) {
            String[] partes = sensoresStr.split(",");
            for (String s : partes) {
                String sensor = s.trim().toUpperCase();
                if (!sensoresDisponiveis.contains(sensor)) {
                    System.out.println("Sensor " + sensor + " não está disponível para este drone.");
                    return;
                }
                sensoresSolicitados.add(sensor);
            }
        }
        MissaoDTO dto = new MissaoDTO(nome, droneId, areaId, inicio, fim, sensoresSolicitados);
        try {
            int missaoId = agendador.agendarMissao(dto, operador);
            System.out.println("Missão agendada com sucesso. ID atribuído: " + missaoId);
        } catch (Exception e) {
            System.out.println("Falha ao agendar missão: " + e.getMessage());
        }
    }

    private void registrarDados() {
        System.out.print("ID da missão: ");
        int missaoId = lerInteiro();
        Missao missao = buscarMissaoPorId(missaoId);
        if (missao == null) {
            System.out.println("Missão não encontrada.");
            return;
        }
        System.out.print("Temperatura (°C) ou deixe em branco: ");
        String tempStr = scanner.nextLine().trim();
        Double temperatura = tempStr.isEmpty() ? null : lerDouble(tempStr);
        System.out.print("Umidade (%) ou deixe em branco: ");
        String umidStr = scanner.nextLine().trim();
        Double umidade = umidStr.isEmpty() ? null : lerDouble(umidStr);
        System.out.print("Pragas observadas (descrição livre) ou deixe em branco: ");
        String pragas = scanner.nextLine().trim();
        DadosColetados dados = new DadosColetados(
                0,
                null,
                temperatura,
                umidade,
                pragas.isEmpty() ? null : pragas,
                LocalDateTime.now()
        );
        missao.adicionarDados(dados);
        System.out.println("Dados registrados na missão.");
    }

    private void gerarRelatorio() {
        List<Missao> missoes = missaoRepo.listar();
        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missão encontrada.");
            return;
        }
        System.out.println("\n==== Relatório de Missões ====");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Missao m : missoes) {
            String areaInfo = m.getArea() != null ? m.getArea().getId() + " (" + m.getArea().getTipoCultivo() + ")" : "N/A";
            String droneInfo = m.getDrone() != null ? String.valueOf(m.getDrone().getId()) : "N/A";
            String inicio = m.getDataHora() != null ? m.getDataHora().format(formatter) : "N/A";
            System.out.println("Missão ID " + m.getId() + " | Área: " + areaInfo + " | Drone: " + droneInfo + " | Início: " + inicio + " | Status: " + m.getStatus());
            List<DadosColetados> dados = m.getDados();
            if (!dados.isEmpty()) {
                DadosColetados ultimo = dados.get(dados.size() - 1);
                System.out.println("  Últimos dados -> Temperatura: " + (ultimo.getTemperatura() != null ? ultimo.getTemperatura() + "°C" : "n/a")
                        + ", Umidade: " + (ultimo.getUmidade() != null ? ultimo.getUmidade() + "%" : "n/a")
                        + (ultimo.getPragas() != null ? ", Pragas: " + ultimo.getPragas() : ""));
            } else {
                System.out.println("  Nenhum dado coletado ainda.");
            }
        }
    }

    private void listarAreas() {
        System.out.println("\n==== Áreas Cadastradas ====");
        Collection<AreaAgricola> areas = areaRepo.listarTodas();
        if (areas.isEmpty()) {
            System.out.println("Nenhuma área cadastrada.");
            return;
        }
        for (AreaAgricola a : areas) {
            System.out.println(
                    "ID: " + a.getId() +
                            " | Localização: " + a.getLocalizacao() +
                            " | Tamanho (ha): " + a.getTamanho() +
                            " | Cultivo: " + a.getTipoCultivo()
            );
        }
    }

    private void listarDrones() {
        System.out.println("\n==== Drones Cadastrados ====");
        Collection<Drone> drones = droneRepo.listarTodos();
        if (drones.isEmpty()) {
            System.out.println("Nenhum drone cadastrado.");
            return;
        }
        for (Drone d : drones) {
            List<String> sensores = sensoresDoDrone.getOrDefault(d.getId(), Collections.emptyList());
            System.out.println(
                    "ID: " + d.getId() +
                            " | Modelo: " + d.getModelo() +
                            " | Status: " + d.getStatus() +
                            " | Sensores: " + (sensores.isEmpty() ? "nenhum" : String.join(",", sensores))
            );
        }
    }

    private void listarMissoes() {
        List<Missao> missoes = missaoRepo.listar();
        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missão cadastrada.");
            return;
        }
        System.out.println("\n==== Missões Cadastradas ====");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Missao m : missoes) {
            System.out.println(
                    "ID: " + m.getId() +
                            ", Início: " + (m.getDataHora() != null ? m.getDataHora().format(formatter) : "N/A") +
                            ", Área: " + (m.getArea() != null ? m.getArea().getId() : "N/A") +
                            ", Drone: " + (m.getDrone() != null ? m.getDrone().getId() : "N/A") +
                            ", Status: " + m.getStatus()
            );
        }
    }

    private StatusDrone escolherStatusDrone() {
        System.out.println("Selecione o status do drone:");
        System.out.println("1. Disponível");
        System.out.println("2. Em missão");
        System.out.println("3. Em manutenção");
        System.out.print("Opção: ");
        String opcao = scanner.nextLine().trim();
        switch (opcao) {
            case "1":
                return StatusDrone.DISPONIVEL;
            case "2":
                return StatusDrone.EM_MISSAO;
            case "3":
                return StatusDrone.EM_MANUTENCAO;
            default:
                System.out.println("Opção inválida. Padrão: Disponível.");
                return StatusDrone.DISPONIVEL;
        }
    }

    private int lerInteiro() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número inteiro: ");
            }
        }
    }

    private double lerDouble() {
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.print("Valor inválido. Digite um número decimal: ");
            }
        }
    }

    private double lerDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Valor numérico inválido: " + str);
        }
    }

    private LocalDateTime lerDataHora() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            String entrada = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(entrada, formatter);
            } catch (Exception e) {
                System.out.print("Formato inválido. Informe no formato dd/MM/yyyy HH:mm: ");
            }
        }
    }

    private Missao buscarMissaoPorId(int id) {
        for (Missao m : missaoRepo.listar()) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void carregarSensoresDeDisco() {
        File f = new File(ARQUIVO_SENSORES);
        if (!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Map) {
                sensoresDoDrone.clear();
                sensoresDoDrone.putAll((Map<Integer, List<String>>) obj);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Aviso: não foi possível carregar sensores de disco: " + e.getMessage());
        }
    }

    private void salvarSensoresEmDisco() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_SENSORES))) {
            oos.writeObject(sensoresDoDrone);
        } catch (IOException e) {
            System.out.println("Aviso: não foi possível salvar sensores de disco: " + e.getMessage());
        }
    }
}
EOF