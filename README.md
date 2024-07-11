# Spring Boot Application with Spring Security, JWT, OAuth2, Lombok, MySQL, JPA

## Descrição

Esta aplicação é uma API desenvolvida em Spring Boot que utiliza Spring Security, JWT, OAuth2, Lombok, MySQL e JPA. A aplicação possui três tabelas principais: `roles`, `users` e `tweets`. A autenticação é baseada em roles, com permissões de `admin` e `basic`.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java.
- **Spring Security**: Framework para autenticação e autorização.
- **JWT (JSON Web Tokens)**: Para autenticação segura com tokens.
- **OAuth2**: Protocolo para autorização.
- **Lombok**: Biblioteca para reduzir o código boilerplate.
- **MySQL**: Sistema de gerenciamento de banco de dados relacional.
- **JPA (Java Persistence API)**: Para mapeamento objeto-relacional.

## Estrutura do Banco de Dados

A aplicação possui três tabelas principais:

1. **roles**:
    - `id` (Long): Identificador único da role.
    - `name` (String): Nome da role (e.g., `admin`, `basic`).

2. **users**:
    - `id` (UUID): Identificador único do usuário.
    - `username` (String): Nome de usuário.
    - `password` (String): Senha do usuário.
    - `roles` (Set<Role>): Roles associadas ao usuário.

3. **tweets**:
    - `id` (Long): Identificador único do tweet.
    - `content` (String): Conteúdo do tweet.
    - `user` (User): Usuário que criou o tweet.

## Funcionalidades

- **Autenticação e Autorização**:
    - Autenticação via JWT e OAuth2.
    - Autorização baseada em roles (`admin` e `basic`).

- **Operações CRUD**:
    - Criação, leitura, atualização e exclusão de tweets.
    - Gerenciamento de usuários e roles.

## Configuração

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- MySQL 8.0 ou superior

### Variáveis de Ambiente

Configure as seguintes variáveis de ambiente para a aplicação:

```plaintext
DATABASE_URL=jdbc:mysql://localhost:3306/mydb
DATABASE_USERNAME=admin
DATABASE_PASSWORD=123
JWT_PUBLIC_KEY=classpath:app.pub
JWT_PRIVATE_KEY=classpath:app.key
```

### Gerando as chaves privadas e públicas

**Não compartilhe essas chaves com ninguém, nem coloque-as no github**

1. Vá até o diretório resources e digite o comando para gerar a chave privada.
```bash
cd src/main/resources
openssl genrsa 
```

2. Crie a chave pública vinculada a chave privada.
```bash
openssl rsa -in nome_da_chave_privada.key -pubout -out nome_da_chave_pública.key 
```

### Instalação

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/sua-aplicacao.git
cd sua-aplicacao
```

2. Configure o banco de dados MySQL conforme necessário e ajuste as variáveis de ambiente no arquivo `application.properties` ou configure-as no ambiente do sistema.

3. Execute o Maven para construir a aplicação:

```bash
mvn clean install
```

4. Execute a aplicação:

```bash
mvn spring-boot:run
```

## Uso

### Endpoints

A aplicação expõe os seguintes endpoints principais:

- **Autenticação**:
    - `POST /login`: Autenticação do usuário.
    - `POST /users`: Registro de novo usuário.
    - `GET /users`: Mostra a lista de usuários apenas para o admin.

- **Tweets**:
    - `GET /feed`: Obter todos os tweets.
    - `POST /tweets`: Criar um novo tweet.
    - `DELETE /tweets/{id}`: Deletar um tweet por ID.

### Exemplo de Requisição

**Autenticação:**

```bash
curl -X POST http://localhost:8080/login \
    -H "Content-Type: application/json" \
    -d '{"username":"admin", "password":"password"}'
```

**Criar um Tweet:**

```bash
curl -X POST http://localhost:8080/tweets \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer <seu-token-jwt>" \
    -d '{"content":"Hello, world!"}'
```
