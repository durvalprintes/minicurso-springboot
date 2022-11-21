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

### Pré-requisitos

É necessário ter instalado a JDK 11, maven e uma base PostgreSQL na máquina local. Indico o uso de _containers_ com o **Docker**, com este projeto. Acesse o site https://www.docker.com/, para visualizar as instruções de instalação, de acordo com o seu sistema operacional.
Primeiramente é necessário criar algumas variáveis de ambiente que, o projeto irá fazer uso, então, no seu terminal do Linux ou, usando Git Bash, execute o comando:
```
nano ~/.bashrc
```
Cole o seguinte texto, substituindo os **asteriscos**, pelos valores que você desejar:
```
#MINICURSO API REST COM SPRING BOOT

#NOME DO USUARIO ROOT
export DB_USER=*****

#SENHA DO USUARIO ROOT
export DB_PASS=*****

#NOME DO BANCO DE DADOS
export DB_NAME=*****

#NOME DO ESQUEMA DA APLICACAO
export DB_SCHEMA=*****

#URL DE CONEXAO
export DB_URL=jdbc:postgresql://localhost:5432/$DB_NAME
```
Feito isso, para criar uma instancia da imagem do Postgres com Docker, basta executar o seguinte comando:
```
docker run --name minicurso -d -e POSTGRES_USER=${DB_USER} -e POSTGRES_PASSWORD=${DB_PASS} -e POSTGRES_DB=${DB_NAME} -e POSTGRES_SCHEMA=${DB_SCHEMA} -v 'C:\docker\volumes\ufopa\minicurso\postgresql\data:/var/lib/postgresql/data' -p 5432:5432 postgres:15-alpine
```
Com o container executando, caso você não tenha uma ferramenta de Administração de banco de dados instalado para criar os _schemas_ necessários, execute os seguintes comandos:
1. Conectar o terminal ao container criado acima:
```
docker exec -it minicurso bash
```
2. Interagir com o Postgres e criar os _schemas_ da aplicação e para os testes, respectivamente:
```
psql -U $POSTGRES_USER -d $POSTGRES_DB -c "CREATE SCHEMA IF NOT EXISTS $POSTGRES_SCHEMA; CREATE SCHEMA IF NOT EXISTS test"
```

### Execução

Para executar o projeto, você poderá fazer uso de uma IDE, como IntelliJ, VSCode, Eclipse, ou executar no terminal o comando maven, aplicando o perfil DEV para desenvolvimento:

```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
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
- Mapeamentos ente Objetos de transferência de dados (DTOs) e entidades com MapStruct;
- Pesquisas com Specifications;
- Validações da entrada de dados;
- Tratamento de exceções;
- Autenticação e autorização;
- Testes unitários e cobertura de testes;
- Documentação com Swagger;
- Monitoramento com Actuator;

## 🎉 Agradecimento <a name = "thanks"></a>
Agradeço ao convite da docente Flávia Monteiro para ministrar e incentivar o compartilhamento do conhecimento entre todos os envolvidos.