# Gerenciador de Projetos e Tarefas

API RESTful para gerenciamento de projetos e tarefas com sistema de autenticação.

## 📋 Sobre o Projeto

Sistema completo para gerenciar projetos e suas respectivas tarefas, permitindo organização eficiente do trabalho com controle de status, prioridades e prazos.

### Funcionalidades

- 🔐 **Autenticação**: Sistema de registro e login de usuários
- 📁 **Projetos**: Criação, listagem e finalização de projetos
- ✅ **Tarefas**: Gerenciamento completo de tarefas com filtros, prioridades e status
- 🔍 **Filtros avançados**: Busca de tarefas por projeto, status e prioridade

## 🚀 Tecnologias

- Java 17
- Spring Boot 3.5.6
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- JWT (JSON Web Token)
- Lombok
- MapStruct
- SpringDoc OpenAPI (Swagger)
- Docker
- Docker Compose
- H2 Database (testes)

## 📦 Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

- [Java JDK 17](https://www.oracle.com/java/technologies/downloads/) ou superior
- [Maven](https://maven.apache.org/download.cgi) (versão 3.6 ou superior)
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## 🔧 Como Executar

### 1. Clone o repositório
```bash
git clone https://github.com/Gaeque/dev.matheuslf.desafio.inscritos.git
```

### 2. Compile o projeto
```bash
mvn clean install
```

### 3. Inicie o banco de dados com Docker Compose
```bash
docker-compose up -d
```

### 4. Execute a aplicação
```bash
mvn spring-boot:run
```

ou execute a aplicação pela sua IDE preferida.

### 5. Acesse a aplicação

- **API**: `http://localhost:8080`
- **Documentação Swagger**: `http://localhost:8080/swagger-ui.html`

## 📚 Documentação da API

A documentação completa da API está disponível via Swagger UI após iniciar a aplicação:
```
http://localhost:8080/swagger-ui.html
```

### Endpoints Disponíveis

#### Autenticação
- `POST /auth/register` - Registrar novo usuário
- `POST /auth/login` - Login de usuário

#### Projetos
- `GET /projects` - Listar todos os projetos
- `POST /projects` - Criar novo projeto
- `PUT /projects/{id}/finalizar` - Finalizar projeto

#### Tarefas
- `GET /tasks` - Buscar tarefas com filtros
- `POST /tasks` - Criar nova tarefa
- `PUT /tasks/{id}/status` - Atualizar status da tarefa
- `DELETE /tasks/{id}` - Deletar tarefa

## 🧪 Testando a API

Você pode testar a API usando:

1. **Swagger UI**: Interface interativa disponível em `/swagger-ui.html`
2. **Postman**: Importe a collection disponível na pasta `/postman` (se disponível)
