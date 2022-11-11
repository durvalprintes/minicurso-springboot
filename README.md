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
Abaixo, segue os demais detalhes, para execução do projeto. Se houver erros ou estiver com dúvidas, você poderá contactar-me para ajuda-lo. 

### Pre-requisitos

É necessário ter instalado a JDK 11, maven e uma base PostgreSQL na máquina local. Indico o uso de _containers_, como o **Docker**, com este projeto. 
Acesse o site https://www.docker.com/, para visualizar as instruções de instalação, de acordo com o seu sistema operacional. 
Feito isso, para criar uma instancia da imagem do Postgres, só executar o seguinte comando:

```
docker run --name minicurso -d -e POSTGRES_USER=app -e POSTGRES_PASSWORD=AppP@ss -p 5432:5432 postgres:15-alpine
```

### Execução

Para executar o projeto, você poderá fazer uso de uma IDE, como IntelliJ, VSCode, Eclipse, ou executar no terminal o comando maven:

```
mvn spring-boot:run
```

Se optar por gerar um executável _JAR_, deverá executar o comando:

```
mvn clean package
```

Em ambos os cenários, você deverá ser capaz de gerar a seguinte saída:

![spring](spring.jpg)

## 🔧 Desenvolvimento <a name = "development"></a>

Os seguintes conceitos, recursos e tecnologias são aplicados no projeto:

- API REST;
- JPA com PostgreSQL;
- CRUD completo de uma entidade mapeada no banco;
- Objetos de transferência de dados (DTOs) com MapStruct;
- Pesquisas com Specifications;
- Validações da entrada de dados;
- Tratamento de exceções;
- Autenticação e autorização;
- Testes unitários e cobertura de testes;
- Documentação com Swagger;

## 🎉 Agradecimento <a name = "thanks"></a>
Agradeço ao convite da docente Flávia Monteiro para ministrar e incentivar o compartilhamento do conhecimento entre todos os envolvidos.