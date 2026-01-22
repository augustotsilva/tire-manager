# tire-manager

Gerenciador de pneus de caminhão.

Para rodar, certifique-se que o docker está rodando na máquina.

Comandos:

1. docker compose up -d
2. mvn spring-boot:run

Resolvi não utilizar MapStruct nos DTOs pois seria overkill.

Cada número de fogo é único, sem duplicados.

Há uma collection do postman nos arquivos do projeto para ser importada, com os endpoints implementados.
