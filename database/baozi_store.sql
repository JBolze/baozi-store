-- =====================================================================
-- Baozi Store — API REST
-- Script de criação do banco de dados (MySQL 8 / MariaDB)
--
-- Trabalho de Desenvolvimento Web Back-End
-- Aluno: José Paulo de Oliveira Bolze — RU 4614623
--
-- Uso:
--   mysql -u root -p < database/baozi_store.sql
-- =====================================================================

CREATE DATABASE IF NOT EXISTS baozi_store
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE baozi_store;

DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS clientes;

-- ---------------------------------------------------------------------
-- Clientes
-- ---------------------------------------------------------------------
CREATE TABLE clientes (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    nome          VARCHAR(120) NOT NULL,
    cliente_desde DATE         NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Produtos
-- ---------------------------------------------------------------------
CREATE TABLE produtos (
    id      BIGINT        NOT NULL AUTO_INCREMENT,
    nome    VARCHAR(120)  NOT NULL,
    preco   DECIMAL(10,2) NOT NULL,
    estoque BIT(1)        NOT NULL,
    PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Pedidos (modelo simplificado: cliente e produto identificados por id)
-- ---------------------------------------------------------------------
CREATE TABLE pedidos (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    cliente_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INT    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT pedidos_chk_quantidade CHECK (quantidade >= 1)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

-- ---------------------------------------------------------------------
-- Dados do cenário descrito no relatório (opcional)
-- ---------------------------------------------------------------------
INSERT INTO clientes (id, nome, cliente_desde) VALUES
    (1, 'José Paulo de Oliveira Bolze 4614623', '2026-03-10');

INSERT INTO produtos (id, nome, preco, estoque) VALUES
    (1, 'Baozi de Carne', 6.50, b'1');

INSERT INTO pedidos (id, cliente_id, produto_id, quantidade) VALUES
    (1, 1, 1, 12);
