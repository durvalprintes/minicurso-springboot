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

É necessário ter instalado a JDK 11, Maven e o PostgreSQL na máquina local,
mas caso você não tenha ou, prefere não instalar, indico o uso de _containers_ com o **Docker**, com este projeto.
Acesse o site https://www.docker.com/, para visualizar as instruções de instalação, de acordo com o seu sistema operacional.
Primeiramente, é necessário criar algumas variáveis de ambiente que, o projeto irá fazer uso. Crie então, na raiz do projeto o arquivo ```.env.dev``` e cole o seguinte texto, substituindo os **asteriscos**, pelos valores que você desejar:
```
#NOME DO USUÁRIO DO BANCO DE DADOS
USER=**********

#SENHA DO USUÁRIO DO BANCO DE DADOS
PASS=**********

#NOME DO BANCO DE DADOS
DATABASE=**********

#NOME DO ESQUEMA DA APLICAÇÃO
SCHEMA=**********

#CAMINHO ABSOLUTO DO USUÁRIO
LOCAL=$HOME
```
Feito isso, vamos criar dois _containers_, um para o banco de dados e outro para o servidor da aplicação e, com todas as configurações necessárias para o ambiente de desenvolvimento da Api, abra o terminal e navegue até o diretório deste projeto e execute o único comando abaixo:
```
docker-compose -p minicurso --env-file .env.dev -f docker/dev.docker-compose.yml up --build
```

### Execução

Se você executou com sucesso o comando anterior, o servidor já está rodando dentro do container da aplicação, com restart automático quando houver mudanças e com suporte para debug da aplicação. No caso de erros internos, o container da aplicação irá parar, somente bastando executa-lo novamente para refletir novos ajustes.

Mas, se estiver com o ambiente local configurado para o desenvolvimento da aplicação, poderá executar no terminal o comando maven, aplicando o perfil **DEV**:
```
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
Através do maven também, você tem a opção de gerar o executável _JAR_, simplesmente aplicando o comando:
```
mvn clean package
```
E para executar, faça:
```
java -jar spring-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
Esta última opção, tem o intuito de gerar o executável **final** de uma versão do projeto, visto que todo o código e dependências estão compiladas e embutidas, não refletindo novos ajustes.  

Em todos os cenários, você deverá ser capaz de gerar a seguinte saída para a aplicação:

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