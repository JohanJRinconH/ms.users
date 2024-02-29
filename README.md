# Proyecto

Test para crear ususarios

## Owner

Johan Rincon - johan.gtr@gmail.com

## Como usar

Realizar un POST al endpoint:

    localhost:8082/api/usuarios/registrar

Con un body con el json ejemplo:

    {
        "name": "Juan Rodriguez",
        "email": "juan@rodriguez.org",
        "password": "hunter2",
        "phones": [
            {
                "number": "1234567",
                "citycode": "1",
                "contrycode": "57"
            }
        ]
    }

## Swagger

    http://localhost:8082/v3/api-docs
    http://localhost:8082/swagger-ui/index.html