# Projeto REST API de Springboot
[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-green)]()

## 📝 Sumário

- [Sobre](#about)
- [Começando](#started)
- [Desenvolvimento](#development)
- [Agradecimento](#thanks)

## 🧐 Sobre <a name = "about"></a>

Projeto REST API, utilizado para o minicurso de Springboot, pertencente à programação de Projeto de Carreiras TI da Universidade Federal do Oeste do Pará - UFOPA, campus de Oriximiná.

## 🚀 Começando <a name = "started"></a>

Projeto é desenvolvido com Maven, Java 11 e Spring Boot 2.7.5. Veja em [Desenvolvimento](#development) para mais detalhes sobre os recursos abordados.
Abaixo, segue os passos para execução do projeto. Se houver erros ou estiver com dúvidas, você poderá contactar-me para ajuda-lo. 

### Pré-requisitos

É necessário instalar na máquina local a JDK 11, o Docker e o _JAR_ do projeto **Tescontainers** para automatização de ambientes, execute o comando de instalação, descrita no [README](https://github.com/durvalprintes/testcontainers-auto-services-prototype/tree/pgsql_only#readme) do projeto.  

### Execução

Execute no terminal o comando maven abaixo:
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```
Através do maven também, você tem a opção de gerar o executável _JAR_, simplesmente aplicando o comando:
```
./mvnw clean package
```
E para executar, faça:
```
java -jar target/spring-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
Em todos os cenários, você deverá ser capaz de gerar a seguinte saída da aplicação:

![spring_output](spring.jpg)

## 🔧 Desenvolvimento <a name = "development"></a>

Os seguintes conceitos, recursos e tecnologias são aplicados no projeto:

- API REST;
- JPA com PostgreSQL;
- CRUD completo de uma entidade mapeada no banco;
- Mapeamentos ente Objetos de transferência de dados (DTOs) e entidades com MapStruct;
- Pesquisas com Specifications;
- Validações da entrada de dados;
- Tratamento de exceções;
- Autenticação e autorização;
- Testes unitários e de integração com Testcontainers e cobertura de testes;
- Documentação com Swagger;
- Monitoramento com Actuator;
- Conteinerização da aplicação para desenvolvimento;

## 🎉 Agradecimento <a name = "thanks"></a>
Agradeço ao convite da docente Flávia Monteiro para ministrar e incentivar o compartilhamento do conhecimento entre todos os envolvidos.