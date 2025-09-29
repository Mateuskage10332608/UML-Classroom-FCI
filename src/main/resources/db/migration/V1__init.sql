-- Criação das tabelas necessárias para armazenar as entidades do sistema.

CREATE TABLE IF NOT EXISTS usuarios (
    id           BIGINT PRIMARY KEY,
    nome         VARCHAR(255) NOT NULL,
    login        VARCHAR(120) NOT NULL UNIQUE,
    senha_hash   VARCHAR(255) NOT NULL,
    tipo         VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS areas_agricolas (
    id            BIGINT PRIMARY KEY,
    localizacao    VARCHAR(255) NOT NULL,
    tamanho        DOUBLE PRECISION,
    tipo_cultivo   VARCHAR(80)
);

CREATE TABLE IF NOT EXISTS drones (
    id      BIGINT PRIMARY KEY,
    modelo  VARCHAR(120) NOT NULL,
    status  VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS missoes (
    id          BIGINT PRIMARY KEY,
    data_hora   TIMESTAMP NOT NULL,
    status      VARCHAR(32) NOT NULL,
    area_id     BIGINT NOT NULL,
    drone_id    BIGINT NOT NULL,
    operador_id BIGINT NOT NULL,
    CONSTRAINT fk_missao_area      FOREIGN KEY (area_id)     REFERENCES areas_agricolas(id),
    CONSTRAINT fk_missao_drone     FOREIGN KEY (drone_id)    REFERENCES drones(id),
    CONSTRAINT fk_missao_operador  FOREIGN KEY (operador_id) REFERENCES usuarios(id)
);

CREATE TABLE IF NOT EXISTS dados_coletados (
    id         BIGINT PRIMARY KEY,
    imagem     BYTEA,
    temperatura DOUBLE PRECISION,
    umidade     DOUBLE PRECISION,
    pragas      VARCHAR(255),
    data_hora   TIMESTAMP NOT NULL,
    missao_id   BIGINT NOT NULL,
    CONSTRAINT fk_dados_missao FOREIGN KEY (missao_id) REFERENCES missoes(id) ON DELETE CASCADE
);

-- Índice para acelerar buscas de missões por drone e horário
CREATE INDEX IF NOT EXISTS idx_missoes_drone_hora ON missoes (drone_id, data_hora);
