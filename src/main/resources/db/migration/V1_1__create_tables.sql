-- ===========================================
-- Criar tabela de veículos
-- ===========================================
CREATE TABLE vehicle
(
    id            SERIAL PRIMARY KEY,
    license_plate VARCHAR(15)      NOT NULL UNIQUE,
    brand         VARCHAR(50)      NOT NULL,
    km            DOUBLE PRECISION NOT NULL DEFAULT 0,
    is_active     BOOLEAN          NOT NULL DEFAULT TRUE
);


-- ===========================================
-- Criar tabela de pneus
-- ===========================================
CREATE TABLE tire
(
    id           SERIAL PRIMARY KEY,
    fire_number  INTEGER     NOT NULL UNIQUE,
    brand        VARCHAR(50) NOT NULL,
    psi_pressure INTEGER     NOT NULL,
    is_allocated BOOLEAN     NOT NULL DEFAULT FALSE
);



-- ===========================================
-- Criar tabela de posições dos pneus dos veículos
-- ===========================================
CREATE TABLE tire_position
(
    id         SERIAL PRIMARY KEY,
    identifier VARCHAR(10) NOT NULL,
    has_tire   BOOLEAN     NOT NULL DEFAULT FALSE,
    vehicle_id INTEGER     NOT NULL,
    tire_id    INTEGER,

    CONSTRAINT fk_tp_vehicle FOREIGN KEY (vehicle_id)
        REFERENCES vehicle (id) ON DELETE CASCADE,

    CONSTRAINT fk_tp_tire FOREIGN KEY (tire_id)
        REFERENCES tire (id) ON DELETE SET NULL,

    -- Garante que cada posição é única dentro do veículo
    CONSTRAINT uq_vehicle_position UNIQUE (vehicle_id, identifier)
);
