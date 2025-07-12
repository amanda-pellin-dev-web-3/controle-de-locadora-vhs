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

- Cadastro e autenticação de usuários com controle de papéis (Admin/Cliente)
- Cadastro, listagem, edição e exclusão de fitas VHS
- Cadastro e listagem de categorias
- Sistema completo de empréstimos com registro de datas
- Controle de devolução de fitas emprestadas
- Registro de status das fitas (disponível, emprestada, indisponível)
- Interface web responsiva com Thymeleaf e CSS customizado

### Como o Sistema Funciona

#### Controle de Usuários e Permissões
O sistema implementa um robusto controle de acesso baseado em papéis:

- **Autenticação**: Utiliza sessões HTTP para manter usuários logados
- **Autorização**: Verificações de papel em cada operação sensível
- **Validações de Entrada**: Implementadas no [`UserService`](src/main/java/br/edu/foz/ifpr/controle_de_locadora_vhs/services/UserService.java):
  - Nome obrigatório e não vazio
  - Email único no sistema
  - Senha com mínimo de 3 caracteres
  - Papel do usuário obrigatório
- **Criptografia**: Senhas são criptografadas usando Spring Security BCrypt

#### Sistema de Empréstimos
O [`LoanController`](src/main/java/br/edu/foz/ifpr/controle_de_locadora_vhs/controllers/LoanController.java) gerencia todo o ciclo de vida dos empréstimos:

- **Registro de Empréstimo**: Apenas administradores podem registrar empréstimos
- **Controle de Status**: Fitas emprestadas têm seu status alterado automaticamente
- **Processo de Devolução**: 
  - Verificação de permissões (apenas ADMIN)
  - Atualização do status das fitas para "DISPONÍVEL"
  - Marcação do empréstimo como devolvido
  - Persistência das alterações no banco de dados

#### Gerenciamento de Fitas VHS
- **Status Dinâmico**: Controle automático do status das fitas durante empréstimos/devoluções
- **Categorização**: Sistema de categorias para organização do acervo
- **Operações CRUD**: Completas para fitas e categorias (restrita a administradores)

### Processo de Desenvolvimento

O desenvolvimento seguiu as etapas:

1. **Arquitetura e Design**
   - Definição das entidades principais (User, VHS, Category, Loan)
   - Implementação do padrão MVC com Spring Boot
   - Estruturação em camadas (Controller, Service, Repository)

2. **Implementação do Backend**
   - Criação dos repositórios JPA para persistência
   - Desenvolvimento dos serviços com regras de negócio
   - Implementação dos controllers REST/Web
   - Sistema de validações e tratamento de erros

3. **Sistema de Segurança e Controle de Acesso**
   - Para integridade do sistema, foram implementados métodos de autenticação e definição de papéis para os usuários (Admin e Cliente)
   - Para cadastro, atualização e exclusão de fitas e categorias, somente o ADMIN poderá efetuar essas ações
   - O primeiro cadastro será sempre um ADMIN
   - Qualquer pessoa pode se cadastrar no site, porém sempre será atribuído CLIENTE para o seu tipo de usuário
   - Somente o ADMIN pode alterar as informações do cliente e promover seu acesso (de cliente para admin)
   - Somente um admin pode excluir um cliente

4. **Interface de Usuário**
   - Implementação das telas com Thymeleaf e integração com o backend
   - Design responsivo com CSS customizado
   - Templates reutilizáveis para diferentes seções do sistema

5. **Configuração e Deploy**
   - Configuração do banco de dados MySQL e propriedades do projeto
   - Configuração do Maven para build e dependências
   
### Como baixar e instalar

1. Clone este repositório:
   ```sh
   git clone https://github.com/seu-usuario/controle-de-locadora-vhs.git
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd controle-de-locadora-vhs
   ```
3. Configure o arquivo [`src/main/resources/application.properties`](src/main/resources/application.properties) com os dados do seu banco MySQL:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/locadora_vhs
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   ```
com os dados do seu banco MySQL.
4. Compile e execute o projeto com Maven:
   ```sh
   ./mvnw spring-boot:run
   ```
5. Acesse o sistema em [http://localhost:8080](http://localhost:8080)

### Resultados Obtidos

Os resultados obtidos com o projeto são:

- Gerenciamento eficiente de fitas VHS e categorias, com operações completas de cadastro, edição, exclusão e listagem.
- Sistema de empréstimos funcional, incluindo registro, controle de status e devolução de fitas.
- Autenticação de usuários com controle de acesso por papéis (Admin/Cliente), garantindo segurança nas operações sensíveis.
- Interface web responsiva e amigável, facilitando o uso por administradores e clientes.
- Persistência dos dados em banco MySQL, com validações e criptografia de senhas para maior integridade e proteção.
- Estrutura modular e organizada, seguindo boas práticas de desenvolvimento com Spring Boot e MVC.
---