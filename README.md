# Proyecto BCI
Api para poder loguear y registrar usuarios
## Herramientas

Framework: Spring boot

Lenguaje: Java

Base de datos:  H2

Compilacion: Gradlew


## Instrucciones
Debe clonar el proyecto y realizar las peticiones por medio de postman, con los siguientes datos:

URL: localhost:8080/proyectobci/v1/sign_up

Request:

    {
        "name": String,
        "email": String,
        "password": String,
        "phones": [
            {
              "number": long,
              "citycode": int,
              "contrycode": String
            }
        ]
    }

URL: localhost:8080/proyectobci/v1/login

    {
        
    "username": "String",
    "password": "String"

    }

Como respuesta debemos obtener lo siguiente:

    {
        "id": "e5c6cf84-8860-4c00-91cd-22d3be28904e",
        "created": "Nov 16, 2021 12:51:43 PM",
        "lastLogin": "Nov 16, 2021 12:51:43 PM",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWxpb0B0ZXN0...",
        "isActive": true,
        "name": "Julio Gonzalez",
        "email": "julio@testssw.cl",
        "password": "a2asfGfdfdf4",
        "phones": [
            {
              "number": 87650009,
              "citycode": 7,
              "contrycode": "25"
            }
        ]
    }

# Desarrollador
Aguilera Martin


