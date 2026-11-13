# Baozi Store — API REST

Trabalho da disciplina **Desenvolvimento Web Back-End**.

Aluno: **José Paulo de Oliveira Bolze** — RU **4614623**

API REST para o controle básico de **clientes**, **produtos** e **pedidos** de uma pequena loja de
pão chinês (*baozi*), desenvolvida com **Java 21 + Spring Boot 3**, **Spring Data JPA**, banco
relacional **MySQL** (perfis alternativos para **MariaDB** e **H2**) e respostas em **JSON**,
seguindo a arquitetura MVC do Spring (`model`, `repository`, `controller`).

---

## 1. Tecnologias

| Tecnologia | Uso no projeto |
| --- | --- |
| Java 21 | Linguagem da aplicação |
| Spring Boot 3.3.5 | Framework base, servidor web embutido (Tomcat) |
| Spring Web | Endpoints REST e serialização JSON |
| Spring Data JPA / Hibernate | Mapeamento objeto-relacional e acesso ao banco |
| Bean Validation | Validação dos dados de entrada |
| MySQL 8.0 | Banco de dados relacional principal |
| MariaDB | Banco alternativo, com o perfil `mariadb` |
| H2 | Banco em memória, com o perfil `h2` |
| Maven | Gerenciamento de dependências e build |
| Postman | Testes manuais dos endpoints |

## 2. Estrutura do projeto

```
baozi-store
├── pom.xml
├── README.md
├── database/baozi_store.sql                  script do banco (DDL + dados do cenário)
├── postman/Baozi-Store.postman_collection.json
└── src/main
    ├── java/br/com/baozistore
    │   ├── BaoziStoreApplication.java        classe main
    │   ├── model                             entidades JPA
    │   │   ├── Cliente.java
    │   │   ├── Produto.java
    │   │   └── Pedido.java
    │   ├── repository                        interfaces Spring Data JPA
    │   │   ├── ClienteRepository.java
    │   │   ├── ProdutoRepository.java
    │   │   └── PedidoRepository.java
    │   ├── controller                        endpoints REST
    │   │   ├── ClienteController.java
    │   │   ├── ProdutoController.java
    │   │   └── PedidoController.java
    │   └── exception                         tratamento centralizado de erros
    │       ├── ApiExceptionHandler.java
    │       ├── RecursoNaoEncontradoException.java
    │       └── RegraDeNegocioException.java
    └── resources
        ├── application.properties            configuração do MySQL (padrão)
        ├── application-mariadb.properties    perfil MariaDB
        └── application-h2.properties         perfil H2 (banco em memória)
```

## 3. Como executar

Pré-requisitos: **Java 21** e **Maven** (ou importar o projeto em uma IDE).

### 3.1 Com MySQL (banco principal)

1. Crie o banco de dados e as tabelas:

```bash
mysql -u root -p < database/baozi_store.sql
```

> Se preferir, crie apenas o banco (`CREATE DATABASE baozi_store;`) e deixe o Hibernate criar as
> tabelas automaticamente (`ddl-auto=update`).

2. Ajuste usuário e senha em `src/main/resources/application.properties` ou defina as variáveis de
   ambiente `DB_USERNAME` e `DB_PASSWORD`.

3. Execute:

```bash
mvn spring-boot:run
```

A API estará em `http://localhost:8080`.

### 3.2 Com MariaDB

O projeto inclui o driver oficial do MariaDB e o perfil `mariadb`:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mariadb
```

O perfil usa a URL padrão `jdbc:mariadb://localhost:3306/baozi_store` (ajustável por `DB_URL`).

### 3.3 Com H2 (sem instalar servidor de banco)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=h2
```

O console do H2 fica disponível em `http://localhost:8080/h2-console`
(JDBC URL `jdbc:h2:mem:baozi_store`, usuário `sa`, sem senha).

### 3.4 Pela IDE

Importe o projeto como *Maven Project* em Eclipse, IntelliJ ou VS Code, execute a classe
`BaoziStoreApplication` e a API estará disponível em `http://localhost:8080`.

## 4. Endpoints

| Método | Endpoint | Descrição | Resposta |
| --- | --- | --- | --- |
| POST | `/clientes` | Cadastrar cliente | 201 Created |
| GET | `/clientes` | Listar todos os clientes | 200 OK |
| GET | `/clientes/{id}` | Consultar cliente por id | 200 OK / 404 |
| PUT | `/clientes/{id}` | Atualizar cliente (opcional no enunciado) | 200 OK / 404 |
| DELETE | `/clientes/{id}` | Apagar cliente | 204 No Content |
| POST | `/produtos` | Cadastrar produto | 201 Created |
| GET | `/produtos` | Listar todos os produtos | 200 OK |
| GET | `/produtos/{id}` | Consultar produto por id | 200 OK / 404 |
| PUT | `/produtos/{id}` | Atualizar produto (opcional no enunciado) | 200 OK / 404 |
| DELETE | `/produtos/{id}` | Apagar produto | 204 No Content |
| POST | `/pedidos` | Registrar pedido | 201 Created / 422 |
| GET | `/pedidos` | Listar todos os pedidos | 200 OK |
| GET | `/pedidos/{id}` | Consultar pedido por id | 200 OK / 404 |
| PUT | `/pedidos/{id}` | Atualizar pedido (opcional no enunciado) | 200 OK / 404 / 422 |
| DELETE | `/pedidos/{id}` | Apagar pedido | 204 No Content |

Erros devolvidos em JSON: `400` dados inválidos (com a relação de campos), `404` recurso não
encontrado e `422` regra de negócio violada (cliente ou produto inexistente em um pedido).

## 5. Exemplos de uso

### Criar cliente

```http
POST /clientes
Content-Type: application/json

{
  "nome": "José Paulo de Oliveira Bolze 4614623",
  "clienteDesde": "2026-03-10"
}
```

### Criar produto

```http
POST /produtos
Content-Type: application/json

{
  "nome": "Baozi de Carne",
  "preco": 6.50,
  "estoque": true
}
```

### Registrar pedido

```http
POST /pedidos
Content-Type: application/json

{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 12
}
```

### Consultar e excluir

```http
GET    /pedidos/1

DELETE /pedidos/1
```

## 6. Banco de dados

| Tabela | Colunas |
| --- | --- |
| `clientes` | `id` (PK), `nome`, `cliente_desde` |
| `produtos` | `id` (PK), `nome`, `preco` decimal(10,2), `estoque` |
| `pedidos` | `id` (PK), `cliente_id`, `produto_id`, `quantidade` (>= 1) |

O script `database/baozi_store.sql` cria o banco, as três tabelas e insere os registros do cenário
descrito no relatório.

## 7. Coleção do Postman

O arquivo `postman/Baozi-Store.postman_collection.json` contém as quinze requisições da API, já com
exemplos de resposta. No Postman: **Import → Upload Files** e selecione o arquivo.

A variável `url` da coleção aponta para `http://localhost:8080`.
