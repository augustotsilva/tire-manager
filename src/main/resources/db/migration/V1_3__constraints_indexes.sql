-- ============================================================
-- Função PostgreSQL para deixar placa em maiúsculas
-- ============================================================

CREATE OR REPLACE FUNCTION enforce_upper_license()
    RETURNS trigger AS
$$
BEGIN
    NEW.license_plate := UPPER(NEW.license_plate);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_upper_license
    BEFORE INSERT OR UPDATE
    ON vehicle
    FOR EACH ROW
EXECUTE FUNCTION enforce_upper_license();


-- ============================================================
-- Constraint PostgreSQL: pneu só pode estar em um veículo
-- ============================================================

ALTER TABLE tire_position
    ADD CONSTRAINT uq_unique_tire_per_vehicle UNIQUE (tire_id);

-- tire_id permite NULL — PostgreSQL trata UNIQUE + NULL como permitido.
-- Isso significa:
-- - tire_id = NULL pode repetir
-- - tire_id = valor → só pode aparecer uma vez


-- ============================================================
-- Índices PostgreSQL
-- ============================================================

-- Busca por placa
CREATE INDEX idx_vehicle_license_plate
    ON vehicle (license_plate);

-- Busca por número de fogo
CREATE INDEX idx_tire_fire_number
    ON tire (fire_number);

-- Busca por todos os pneus de um veículo
CREATE INDEX idx_tire_position_vehicle
    ON tire_position (vehicle_id);
