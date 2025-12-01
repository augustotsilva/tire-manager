-- ============================================================
-- VEÍCULOS
-- ============================================================
INSERT INTO vehicle (license_plate, brand, km, is_active)
VALUES ('ABC1A23', 'SCANIA', 150000, TRUE),
       ('DEF4B56', 'VOLVO', 320000, TRUE),
       ('GHI7C89', 'MERCEDES_BENZ', 50000, TRUE),
       ('JKL2D34', 'DAF', 420000, FALSE),
       ('MNO9E12', 'IVECO', 210000, TRUE);



-- ============================================================
-- PNEUS
-- 24 pneus com várias marcas e pressões diferentes
-- ============================================================
INSERT INTO tire (id, fire_number, brand, psi_pressure, is_allocated)
VALUES (1001, 1001, 'MICHELIN', 110, FALSE),
       (1002, 1002, 'MICHELIN', 112, FALSE),
       (1003, 1003, 'MICHELIN', 108, FALSE),
       (1004, 1004, 'MICHELIN', 115, FALSE),
       (1005, 1005, 'MICHELIN', 113, FALSE),
       (1006, 1006, 'MICHELIN', 109, FALSE),

       (2001, 2001, 'PIRELLI', 120, FALSE),
       (2002, 2002, 'PIRELLI', 118, FALSE),
       (2003, 2003, 'PIRELLI', 122, FALSE),
       (2004, 2004, 'PIRELLI', 119, FALSE),
       (2005, 2005, 'PIRELLI', 121, FALSE),
       (2006, 2006, 'PIRELLI', 117, FALSE),

       (3001, 3001, 'GOODYEAR', 125, FALSE),
       (3002, 3002, 'GOODYEAR', 124, FALSE),
       (3003, 3003, 'GOODYEAR', 126, FALSE),
       (3004, 3004, 'GOODYEAR', 123, FALSE),
       (3005, 3005, 'GOODYEAR', 127, FALSE),
       (3006, 3006, 'GOODYEAR', 122, FALSE),

       (4001, 4001, 'CONTINENTAL', 115, FALSE),
       (4002, 4002, 'CONTINENTAL', 116, FALSE),
       (4003, 4003, 'CONTINENTAL', 114, FALSE),
       (4004, 4004, 'CONTINENTAL', 117, FALSE),
       (4005, 4005, 'CONTINENTAL', 118, FALSE),
       (4006, 4006, 'CONTINENTAL', 113, FALSE);

-- ============================================================
-- POSIÇÕES DE PNEU POR VEÍCULO
-- Cada veículo recebe 6 posições: A B C D E F
-- ============================================================
-- Veículo 1 (ID=1)
INSERT INTO tire_position (identifier, has_tire, vehicle_id, tire_id)
VALUES ('A', FALSE, 1, NULL),
       ('B', FALSE, 1, NULL),
       ('C', FALSE, 1, NULL),
       ('D', FALSE, 1, NULL),
       ('E', FALSE, 1, NULL),
       ('F', FALSE, 1, NULL);

-- Veículo 2 (ID=2)
INSERT INTO tire_position (identifier, has_tire, vehicle_id, tire_id)
VALUES ('A', FALSE, 2, NULL),
       ('B', FALSE, 2, NULL),
       ('C', FALSE, 2, NULL),
       ('D', FALSE, 2, NULL),
       ('E', FALSE, 2, NULL),
       ('F', FALSE, 2, NULL);

-- Veículo 3 (ID=3)
INSERT INTO tire_position (identifier, has_tire, vehicle_id, tire_id)
VALUES ('A', FALSE, 3, NULL),
       ('B', FALSE, 3, NULL),
       ('C', FALSE, 3, NULL),
       ('D', FALSE, 3, NULL),
       ('E', FALSE, 3, NULL),
       ('F', FALSE, 3, NULL);

-- Veículo 4 (ID=4)
INSERT INTO tire_position (identifier, has_tire, vehicle_id, tire_id)
VALUES ('A', FALSE, 4, NULL),
       ('B', FALSE, 4, NULL),
       ('C', FALSE, 4, NULL),
       ('D', FALSE, 4, NULL),
       ('E', FALSE, 4, NULL),
       ('F', FALSE, 4, NULL);

-- Veículo 5 (ID=5)
INSERT INTO tire_position (identifier, has_tire, vehicle_id, tire_id)
VALUES ('A', FALSE, 5, NULL),
       ('B', FALSE, 5, NULL),
       ('C', FALSE, 5, NULL),
       ('D', FALSE, 5, NULL),
       ('E', FALSE, 5, NULL),
       ('F', FALSE, 5, NULL);

-- ============================================================
-- ALGUNS PNEUS JÁ ALOCADOS PARA TESTAR O ENDPOINT DE CONSULTA
-- ============================================================

UPDATE tire_position
SET tire_id  = 1001,
    has_tire = TRUE
WHERE vehicle_id = 1
  AND identifier = 'A';
UPDATE tire
SET is_allocated = TRUE
WHERE id = 1001;

UPDATE tire_position
SET tire_id  = 1002,
    has_tire = TRUE
WHERE vehicle_id = 1
  AND identifier = 'B';
UPDATE tire
SET is_allocated = TRUE
WHERE id = 1002;

UPDATE tire_position
SET tire_id  = 2001,
    has_tire = TRUE
WHERE vehicle_id = 2
  AND identifier = 'C';
UPDATE tire
SET is_allocated = TRUE
WHERE fire_number = 2001;

UPDATE tire_position
SET tire_id  = 2002,
    has_tire = TRUE
WHERE vehicle_id = 2
  AND identifier = 'D';
UPDATE tire
SET is_allocated = TRUE
WHERE fire_number = 2002;

UPDATE tire_position
SET tire_id  = 3001,
    has_tire = TRUE
WHERE vehicle_id = 4
  AND identifier = 'A';
UPDATE tire
SET is_allocated = TRUE
WHERE fire_number = 3001;