# API Gateway Concessionária

Este repositório contém a **API Gateway** do sistema de concessionária, responsável por rotear as requisições para os microserviços internos utilizando **Spring Cloud Eureka** para descoberta de serviços.

## Descrição:

A aplicação funciona como ponto de entrada único (FACHADA) para os seguintes microserviços:

* **api-funcionarios** – Gerenciamento de funcionários
* **api-vendas** – Controle de vendas e negociações
* **api-clientes** – Cadastro e gerenciamento de clientes
* **api-veiculos** – Cadastro e controle de veículos
* **api-auth** – Autenticação e autorização dos usuários

O **Eureka Server** é utilizado para registrar e descobrir automaticamente as portas e endereços dos microserviços, evitando configurações fixas no Gateway.

Cada microserviço possui seu próprio **banco PostgreSQL** e roda em um container separado, coordenados por **Docker Compose**.

## Tecnologias Utilizadas:

* Java / Spring Boot
* Spring Cloud Gateway
* Spring Cloud Eureka
* Docker / Docker Compose
* PostgreSQL

## Como Executar o Projeto:

### 1. Clonar todos os repositórios

Baixe este repositório e também os repositórios dos microserviços:

* `api-funcionarios`
* `api-vendas`
* `api-clientes`
* `api-veiculos`
* `api-auth`
* `eureka-server`

Coloque todos os diretórios dentro da **mesma pasta** local.

### 2. Subir os containers

Na primeira vez, execute:

```bash
docker-compose up --build
```

Nas próximas execuções, basta usar:

```bash
docker-compose up
```

Isso irá iniciar:

* Eureka Server
* API Gateway
* Todos os microserviços
* Um container PostgreSQL para cada microserviço

---

## Hospedagem:

No momento, os microserviços ainda **não estão hospedados em nenhum ambiente externo**. Toda a execução ocorre localmente via Docker, Por isso é necessário baixar cada repositorío, e adicionar na mesma pasta.
