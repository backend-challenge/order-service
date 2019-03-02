# Invillia Order Server

Link do Swagger
http://localhost:8070/swagger-ui.html#/

## Requisitos
Para construir a aplicação é necessário que o container com o banco de dados já esteja rodando, caso não esteja é só rodar o seguinte comando:
    
    docker pull mysql
    docker run --name mysql-database -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=backend_challenge -e MYSQL_USER=springuser -e MYSQL_PASSWORD=ThePassword -d mysql:8



## Como rodar a aplicação

1) Fazer checkout do projeto.
2) Fazer o build do projeto com o comando:
        
        mvn clean package
3) Construir o imagem com o Dockerfile: 

        docker build . -t docker-store-service
4) Subir a aplicação no container: 

        docker run -p 8090:8090 --name docker-store-service --link mysql-database:mysql -d docker-store-service

## Funcionalidades
    Neste micro-serviço foi implementado as seguintes funcionalidades:
        1) Criação de uma nova ordem com seus itens
        2) Confirmar uma ordem já criada, para que esteja apta para ser paga
        3) Listagem das ordens filtrando por seus atributos.
