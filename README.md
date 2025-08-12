                                               Desafio da Estante


Descrição do Projeto
Este projeto, desenvolvido como parte do curso "Oracle ONE e Alura", é uma aplicação de console em Java que interage com a API do Gutendex para buscar livros e gerenciá-los em um banco de dados relacional.

A aplicação permite ao usuário:

Buscar e salvar livros no banco de dados.

Listar todos os livros salvos.

Listar autores dos livros salvos.

Listar autores que nasceram em um determinado ano.

Listar livros em um idioma específico.

Tecnologias Utilizadas
Java 21: Linguagem de programação.

Spring Boot 3.5.4: Framework para facilitar a criação da aplicação.

Spring Data JPA: Para abstrair a comunicação com o banco de dados.

PostgreSQL: Banco de dados relacional para persistir os dados dos livros e autores.

Maven: Gerenciador de dependências.

Como Configurar e Executar o Projeto
Para rodar o projeto localmente, siga os passos abaixo:

Pré-requisitos
Certifique-se de que você tem os seguintes softwares instalados:

JDK 21 ou superior.

Um banco de dados PostgreSQL.

Uma IDE de desenvolvimento (como IntelliJ IDEA ou VS Code).

Configuração do Banco de Dados
Crie um banco de dados chamado gutendex no seu servidor PostgreSQL.

Atualize o arquivo src/main/resources/application.properties com as suas credenciais do banco de dados. Para maior segurança, é recomendado usar variáveis de ambiente.

Exemplo de application.properties:

Properties

spring.datasource.url=jdbc:postgresql://${DB_HOST}:5432/gutendex
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.format-sql=true
Execução da Aplicação
Defina as variáveis de ambiente com suas credenciais do PostgreSQL. Se estiver usando o IntelliJ, você pode configurá-las em Run > Edit Configurations > Environment variables.

DB_HOST=localhost

DB_USER=seu_usuario

DB_PASSWORD=sua_senha

Execute a classe principal da aplicação OdesafiodaestanteApplication.java.

A aplicação será iniciada e um menu de opções será exibido no console para interação.

Estrutura do Projeto
dto: Contém os Data Transfer Objects (DTOs) para mapear os dados da API Gutendex.

model: Contém as classes de entidade que representam o modelo de dados do banco.

repository: Contém as interfaces de repositório do Spring Data JPA.

service: Contém as classes de serviço que implementam a lógica de negócio.

OdesafiodaestanteApplication.java: Classe principal para iniciar a aplicação.

Autor
Sergio de Oliveira Rafael

LinkedIn: www.linkedin.com/in/sergio-de-oliveira-rafael-47ba29286






