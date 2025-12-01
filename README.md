# tire-manager

1. Certifique-se que o docker está rodando na máquina
2. docker compose up -d
3. mvn spring-boot:run

Resolvi não utilizar MapStruct nos DTOs pois seria overkill.

Cada número de fogo é único, sem duplicados.

.

Estavam acontecendo alguns erros de ID no meu banco, por já ter mudado as migrations algumas vezes.

Provavelmente não ocorrerá, mas deixo aqui o comando caso necessário:

###### SELECT setval(pg_get_serial_sequence('vehicle', 'id'), (SELECT MAX(id) FROM vehicle));