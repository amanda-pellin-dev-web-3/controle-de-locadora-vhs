# Atividade Avaliativa - Locadora fitas VHS

**Lembrar de ajustar o aplication.properties**

## Sobre o Projeto

Este projeto é um sistema web para controle de locadora de fitas VHS, desenvolvido como atividade avaliativa. O objetivo é permitir o cadastro, listagem e gerenciamento de fitas VHS, categorias, usuários e empréstimos.

### Tecnologias Utilizadas

- Java 21
- Spring Boot (Web, Data JPA, Thymeleaf)
- MySQL
- Lombok
- Maven

### Funcionalidades Implementadas

- Cadastro e autenticação de usuários
- Cadastro, listagem, edição e exclusão de fitas VHS
- Cadastro e listagem de categorias
- Registro de status das fitas (disponível, emprestada, indisponível)
- Interface web responsiva com Thymeleaf e CSS customizado

### Processo de Desenvolvimento

O desenvolvimento seguiu as etapas:
1. Definição das entidades principais (VHS, Category, User, Loan, TapeStatus)
2. Criação dos repositórios, serviços e controllers para cada entidade
3. Implementação das telas com Thymeleaf e integração com o backend
4. Configuração do banco de dados MySQL e propriedades do projeto
5. Testes das funcionalidades principais

### Como baixar e instalar

1. Clone este repositório:
   ```sh
   git clone https://github.com/seu-usuario/controle-de-locadora-vhs.git
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd controle-de-locadora-vhs
   ```
3. Configure o arquivo `src/main/resources/application.properties` com os dados do seu banco MySQL.
4. Compile e execute o projeto com Maven:
   ```sh
   ./mvnw spring-boot:run
   ```
5. Acesse o sistema em [http://localhost:8080](http://localhost:8080)

### Resultados Obtidos

O sistema permite gerenciar fitas VHS e categorias de forma simples, com autenticação de usuários e interface amigável. O projeto pode ser expandido para incluir mais funcionalidades, como relatórios e controle de empréstimos detalhado.

---