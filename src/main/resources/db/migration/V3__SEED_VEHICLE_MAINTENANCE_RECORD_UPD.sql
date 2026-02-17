-- Script de seed data para VEHICLE y MAINTENANCE_RECORD
-- Idempotente: puede ejecutarse múltiples veces sin duplicar datos

-- ============================================
-- INSERTAR VEHÍCULOS (50 total: 35 autos + 15 camiones)
-- ============================================

-- Autos (35 vehículos)
INSERT INTO `VEHICLE` (
    created_at, updated_at, version, deleted, DTYPE,
    brand, model, year, plate, mileage_km, engine_displacement_cc,
    car_type, number_of_doors, passenger_capacity, trunk_capacity_liters,
    last_maintenance_at, last_maintenance_cost, last_maintenance_by
)
SELECT * FROM (
    SELECT 
        NOW() as created_at,
        NOW() as updated_at,
        0 as version,
        FALSE as deleted,
        'Car' as DTYPE,
        brand,
        model,
        year,
        plate,
        mileage_km,
        engine_displacement_cc,
        car_type,
        number_of_doors,
        passenger_capacity,
        trunk_capacity_liters,
        NULL as last_maintenance_at,
        NULL as last_maintenance_cost,
        NULL as last_maintenance_by
    FROM (
        SELECT 'Toyota' as brand, 'Corolla' as model, 2020 as year, 'ABC123' as plate, 45000 as mileage_km, 1800 as engine_displacement_cc, 'SEDAN' as car_type, 4 as number_of_doors, 5 as passenger_capacity, 470 as trunk_capacity_liters
        UNION ALL SELECT 'Honda', 'Civic', 2021, 'DEF456', 32000, 1600, 'SEDAN', 4, 5, 420
        UNION ALL SELECT 'Ford', 'Focus', 2019, 'GHI789', 58000, 2000, 'HATCHBACK', 5, 5, 316
        UNION ALL SELECT 'Volkswagen', 'Golf', 2022, 'JKL012', 15000, 1400, 'HATCHBACK', 5, 5, 380
        UNION ALL SELECT 'Chevrolet', 'Cruze', 2020, 'MNO345', 42000, 1600, 'SEDAN', 4, 5, 445
        UNION ALL SELECT 'Fiat', 'Cronos', 2021, 'PQR678', 28000, 1300, 'SEDAN', 4, 5, 525
        UNION ALL SELECT 'Peugeot', '208', 2022, 'STU901', 12000, 1200, 'HATCHBACK', 5, 5, 311
        UNION ALL SELECT 'Renault', 'Logan', 2019, 'VWX234', 65000, 1600, 'SEDAN', 4, 5, 510
        UNION ALL SELECT 'Toyota', 'Yaris', 2021, 'YZA567', 25000, 1500, 'HATCHBACK', 5, 5, 286
        UNION ALL SELECT 'Nissan', 'Versa', 2020, 'BCD890', 38000, 1600, 'SEDAN', 4, 5, 480
        UNION ALL SELECT 'Hyundai', 'HB20', 2022, 'EFG123', 18000, 1000, 'HATCHBACK', 5, 5, 295
        UNION ALL SELECT 'Ford', 'Fiesta', 2019, 'HIJ456', 52000, 1600, 'HATCHBACK', 5, 5, 290
        UNION ALL SELECT 'Volkswagen', 'Polo', 2021, 'KLM789', 30000, 1000, 'HATCHBACK', 5, 5, 280
        UNION ALL SELECT 'Chevrolet', 'Onix', 2020, 'NOP012', 40000, 1200, 'HATCHBACK', 5, 5, 280
        UNION ALL SELECT 'Fiat', 'Argo', 2022, 'QRS345', 10000, 1300, 'HATCHBACK', 5, 5, 300
        UNION ALL SELECT 'Toyota', 'Camry', 2021, 'TUV678', 22000, 2500, 'SEDAN', 4, 5, 524
        UNION ALL SELECT 'Honda', 'Accord', 2020, 'WXY901', 35000, 2000, 'SEDAN', 4, 5, 473
        UNION ALL SELECT 'Ford', 'Mondeo', 2019, 'ZAB234', 48000, 2000, 'SEDAN', 4, 5, 550
        UNION ALL SELECT 'BMW', '320i', 2021, 'CDE567', 28000, 2000, 'SEDAN', 4, 5, 480
        UNION ALL SELECT 'Mercedes-Benz', 'C200', 2022, 'FGH890', 15000, 1500, 'SEDAN', 4, 5, 455
        UNION ALL SELECT 'Audi', 'A3', 2020, 'IJK123', 42000, 1400, 'SEDAN', 4, 5, 425
        UNION ALL SELECT 'Volkswagen', 'Jetta', 2021, 'LMN456', 32000, 2000, 'SEDAN', 4, 5, 510
        UNION ALL SELECT 'Chevrolet', 'Malibu', 2019, 'OPQ789', 55000, 2500, 'SEDAN', 4, 5, 520
        UNION ALL SELECT 'Toyota', 'RAV4', 2022, 'RST012', 8000, 2000, 'SUV', 5, 5, 580
        UNION ALL SELECT 'Honda', 'CR-V', 2021, 'UVW345', 25000, 1800, 'SUV', 5, 5, 522
        UNION ALL SELECT 'Ford', 'Escape', 2020, 'XYZ678', 38000, 2000, 'SUV', 5, 5, 592
        UNION ALL SELECT 'Nissan', 'Kicks', 2022, 'ABC234', 12000, 1600, 'SUV', 5, 5, 428
        UNION ALL SELECT 'Hyundai', 'Creta', 2021, 'DEF567', 30000, 1600, 'SUV', 5, 5, 433
        UNION ALL SELECT 'Peugeot', '3008', 2020, 'GHI890', 40000, 1600, 'SUV', 5, 5, 520
        UNION ALL SELECT 'Renault', 'Duster', 2019, 'JKL123', 50000, 1600, 'SUV', 5, 5, 408
        UNION ALL SELECT 'Toyota', 'Hilux', 2021, 'MNO456', 35000, 2400, 'PICKUP', 4, 5, 1200
        UNION ALL SELECT 'Ford', 'Ranger', 2022, 'PQR789', 18000, 2000, 'PICKUP', 4, 5, 1200
        UNION ALL SELECT 'Chevrolet', 'S10', 2020, 'STU012', 42000, 2000, 'PICKUP', 4, 5, 1200
        UNION ALL SELECT 'Volkswagen', 'Amarok', 2021, 'VWX345', 30000, 2000, 'PICKUP', 4, 5, 1200
        UNION ALL SELECT 'Nissan', 'Frontier', 2019, 'YZA678', 48000, 2500, 'PICKUP', 4, 5, 1200
    ) as cars
) as vehicle_data
WHERE NOT EXISTS (
    SELECT 1 FROM `VEHICLE` v WHERE v.plate = vehicle_data.plate
);

-- Camiones (15 vehículos)
INSERT INTO `VEHICLE` (
    created_at, updated_at, version, deleted, DTYPE,
    brand, model, year, plate, mileage_km, engine_displacement_cc,
    truck_type, load_capacity_tons, axles,
    last_maintenance_at, last_maintenance_cost, last_maintenance_by
)
SELECT * FROM (
    SELECT 
        NOW() as created_at,
        NOW() as updated_at,
        0 as version,
        FALSE as deleted,
        'Truck' as DTYPE,
        brand,
        model,
        year,
        plate,
        mileage_km,
        engine_displacement_cc,
        truck_type,
        load_capacity_tons,
        axles,
        NULL as last_maintenance_at,
        NULL as last_maintenance_cost,
        NULL as last_maintenance_by
    FROM (
        SELECT 'Mercedes-Benz' as brand, 'Actros' as model, 2020 as year, 'TRK001' as plate, 180000 as mileage_km, 12000 as engine_displacement_cc, 'HEAVY' as truck_type, 20 as load_capacity_tons, 6 as axles
        UNION ALL SELECT 'Volvo', 'FH16', 2021, 'TRK002', 150000, 16000, 'HEAVY', 25, 6
        UNION ALL SELECT 'Scania', 'R450', 2019, 'TRK003', 200000, 13000, 'HEAVY', 22, 6
        UNION ALL SELECT 'Iveco', 'Stralis', 2022, 'TRK004', 120000, 11000, 'HEAVY', 20, 6
        UNION ALL SELECT 'MAN', 'TGX', 2020, 'TRK005', 170000, 12000, 'HEAVY', 23, 6
        UNION ALL SELECT 'Mercedes-Benz', 'Atego', 2021, 'TRK006', 95000, 5000, 'MEDIUM', 8, 3
        UNION ALL SELECT 'Volvo', 'FM', 2019, 'TRK007', 110000, 6000, 'MEDIUM', 10, 3
        UNION ALL SELECT 'Scania', 'P320', 2022, 'TRK008', 80000, 5000, 'MEDIUM', 9, 3
        UNION ALL SELECT 'Iveco', 'Tector', 2020, 'TRK009', 100000, 4500, 'MEDIUM', 7, 3
        UNION ALL SELECT 'Ford', 'Cargo', 2021, 'TRK010', 85000, 4000, 'MEDIUM', 6, 2
        UNION ALL SELECT 'Mercedes-Benz', 'Sprinter', 2019, 'TRK011', 130000, 2200, 'LIGHT', 3, 2
        UNION ALL SELECT 'Volkswagen', 'Delivery', 2022, 'TRK012', 70000, 2000, 'LIGHT', 2, 2
        UNION ALL SELECT 'Fiat', 'Ducato', 2020, 'TRK013', 95000, 2400, 'LIGHT', 3, 2
        UNION ALL SELECT 'Iveco', 'Daily', 2021, 'TRK014', 80000, 3000, 'LIGHT', 3, 2
        UNION ALL SELECT 'Renault', 'Master', 2019, 'TRK015', 110000, 2500, 'LIGHT', 2, 2
    ) as trucks
) as vehicle_data
WHERE NOT EXISTS (
    SELECT 1 FROM `VEHICLE` v WHERE v.plate = vehicle_data.plate
);

-- ============================================
-- INSERTAR REGISTROS DE MANTENIMIENTO
-- ============================================

-- Mantenimientos para vehículos (1-5 por vehículo, fechas en últimos 18 meses)
INSERT INTO maintenance_record (
    created_at, updated_at, version, deleted,
    vehicle_id, maintenance_at, cost, performed_by, notes, current_km
)
SELECT 
    NOW() as created_at,
    NOW() as updated_at,
    0 as version,
    FALSE as deleted,
    v.id as vehicle_id,
    maintenance_at,
    cost,
    performed_by,
    notes,
    current_km
FROM (
    -- Mantenimientos para ABC123 (Toyota Corolla)
    SELECT 'ABC123' as plate, DATE_SUB(NOW(), INTERVAL 3 MONTH) as maintenance_at, 45000.00 as cost, 'Taller Central' as performed_by, 'Cambio de aceite y filtros' as notes, 42000 as current_km
    UNION ALL SELECT 'ABC123', DATE_SUB(NOW(), INTERVAL 8 MONTH), 52000.00, 'Taller Central', 'Revisión general y alineación', 38000
    UNION ALL SELECT 'ABC123', DATE_SUB(NOW(), INTERVAL 14 MONTH), 48000.00, 'Taller Norte', 'Cambio de pastillas de freno', 32000
    
    -- Mantenimientos para DEF456 (Honda Civic)
    UNION ALL SELECT 'DEF456', DATE_SUB(NOW(), INTERVAL 2 MONTH), 38000.00, 'Taller Sur', 'Cambio de aceite', 30000
    UNION ALL SELECT 'DEF456', DATE_SUB(NOW(), INTERVAL 10 MONTH), 45000.00, 'Taller Sur', 'Revisión de neumáticos', 25000
    
    -- Mantenimientos para GHI789 (Ford Focus)
    UNION ALL SELECT 'GHI789', DATE_SUB(NOW(), INTERVAL 1 MONTH), 55000.00, 'Taller Central', 'Cambio de aceite y filtro de aire', 56000
    UNION ALL SELECT 'GHI789', DATE_SUB(NOW(), INTERVAL 6 MONTH), 62000.00, 'Taller Central', 'Revisión de frenos y suspensión', 52000
    UNION ALL SELECT 'GHI789', DATE_SUB(NOW(), INTERVAL 12 MONTH), 48000.00, 'Taller Este', 'Cambio de correa de distribución', 45000
    
    -- Mantenimientos para JKL012 (Volkswagen Golf)
    UNION ALL SELECT 'JKL012', DATE_SUB(NOW(), INTERVAL 1 MONTH), 42000.00, 'Taller Oeste', 'Primer servicio', 14000
    
    -- Mantenimientos para MNO345 (Chevrolet Cruze)
    UNION ALL SELECT 'MNO345', DATE_SUB(NOW(), INTERVAL 4 MONTH), 50000.00, 'Taller Central', 'Cambio de aceite y revisión', 40000
    UNION ALL SELECT 'MNO345', DATE_SUB(NOW(), INTERVAL 11 MONTH), 58000.00, 'Taller Central', 'Revisión completa y alineación', 35000
    
    -- Mantenimientos para PQR678 (Fiat Cronos)
    UNION ALL SELECT 'PQR678', DATE_SUB(NOW(), INTERVAL 3 MONTH), 35000.00, 'Taller Sur', 'Cambio de aceite', 27000
    UNION ALL SELECT 'PQR678', DATE_SUB(NOW(), INTERVAL 9 MONTH), 40000.00, 'Taller Sur', 'Revisión de neumáticos', 22000
    
    -- Mantenimientos para STU901 (Peugeot 208)
    UNION ALL SELECT 'STU901', DATE_SUB(NOW(), INTERVAL 2 MONTH), 38000.00, 'Taller Norte', 'Primer servicio', 11000
    
    -- Mantenimientos para VWX234 (Renault Logan)
    UNION ALL SELECT 'VWX234', DATE_SUB(NOW(), INTERVAL 2 MONTH), 48000.00, 'Taller Este', 'Cambio de aceite y filtros', 63000
    UNION ALL SELECT 'VWX234', DATE_SUB(NOW(), INTERVAL 7 MONTH), 55000.00, 'Taller Este', 'Revisión de frenos', 58000
    UNION ALL SELECT 'VWX234', DATE_SUB(NOW(), INTERVAL 13 MONTH), 52000.00, 'Taller Oeste', 'Cambio de correa', 52000
    
    -- Mantenimientos para YZA567 (Toyota Yaris)
    UNION ALL SELECT 'YZA567', DATE_SUB(NOW(), INTERVAL 3 MONTH), 40000.00, 'Taller Central', 'Cambio de aceite', 24000
    UNION ALL SELECT 'YZA567', DATE_SUB(NOW(), INTERVAL 10 MONTH), 45000.00, 'Taller Central', 'Revisión general', 20000
    
    -- Mantenimientos para BCD890 (Nissan Versa)
    UNION ALL SELECT 'BCD890', DATE_SUB(NOW(), INTERVAL 5 MONTH), 50000.00, 'Taller Sur', 'Cambio de aceite y filtros', 36000
    UNION ALL SELECT 'BCD890', DATE_SUB(NOW(), INTERVAL 12 MONTH), 58000.00, 'Taller Sur', 'Revisión completa', 30000
    
    -- Mantenimientos para EFG123 (Hyundai HB20)
    UNION ALL SELECT 'EFG123', DATE_SUB(NOW(), INTERVAL 2 MONTH), 35000.00, 'Taller Norte', 'Primer servicio', 17000
    
    -- Mantenimientos para HIJ456 (Ford Fiesta)
    UNION ALL SELECT 'HIJ456', DATE_SUB(NOW(), INTERVAL 1 MONTH), 45000.00, 'Taller Central', 'Cambio de aceite', 51000
    UNION ALL SELECT 'HIJ456', DATE_SUB(NOW(), INTERVAL 6 MONTH), 52000.00, 'Taller Central', 'Revisión de frenos', 48000
    UNION ALL SELECT 'HIJ456', DATE_SUB(NOW(), INTERVAL 13 MONTH), 48000.00, 'Taller Este', 'Cambio de filtros', 42000
    
    -- Mantenimientos para KLM789 (Volkswagen Polo)
    UNION ALL SELECT 'KLM789', DATE_SUB(NOW(), INTERVAL 4 MONTH), 40000.00, 'Taller Oeste', 'Cambio de aceite', 29000
    UNION ALL SELECT 'KLM789', DATE_SUB(NOW(), INTERVAL 11 MONTH), 45000.00, 'Taller Oeste', 'Revisión general', 25000
    
    -- Mantenimientos para NOP012 (Chevrolet Onix)
    UNION ALL SELECT 'NOP012', DATE_SUB(NOW(), INTERVAL 3 MONTH), 42000.00, 'Taller Sur', 'Cambio de aceite y filtros', 39000
    UNION ALL SELECT 'NOP012', DATE_SUB(NOW(), INTERVAL 9 MONTH), 50000.00, 'Taller Sur', 'Revisión completa', 34000
    
    -- Mantenimientos para QRS345 (Fiat Argo)
    UNION ALL SELECT 'QRS345', DATE_SUB(NOW(), INTERVAL 1 MONTH), 38000.00, 'Taller Norte', 'Primer servicio', 9500
    
    -- Mantenimientos para TUV678 (Toyota Camry)
    UNION ALL SELECT 'TUV678', DATE_SUB(NOW(), INTERVAL 2 MONTH), 65000.00, 'Taller Central', 'Cambio de aceite premium', 21000
    
    -- Mantenimientos para WXY901 (Honda Accord)
    UNION ALL SELECT 'WXY901', DATE_SUB(NOW(), INTERVAL 4 MONTH), 60000.00, 'Taller Sur', 'Cambio de aceite y revisión', 34000
    UNION ALL SELECT 'WXY901', DATE_SUB(NOW(), INTERVAL 11 MONTH), 70000.00, 'Taller Sur', 'Revisión completa', 29000
    
    -- Mantenimientos para ZAB234 (Ford Mondeo)
    UNION ALL SELECT 'ZAB234', DATE_SUB(NOW(), INTERVAL 3 MONTH), 62000.00, 'Taller Este', 'Cambio de aceite y filtros', 47000
    UNION ALL SELECT 'ZAB234', DATE_SUB(NOW(), INTERVAL 10 MONTH), 75000.00, 'Taller Este', 'Revisión de frenos y suspensión', 42000
    
    -- Mantenimientos para CDE567 (BMW 320i)
    UNION ALL SELECT 'CDE567', DATE_SUB(NOW(), INTERVAL 1 MONTH), 85000.00, 'Taller Premium', 'Servicio oficial BMW', 27500
    UNION ALL SELECT 'CDE567', DATE_SUB(NOW(), INTERVAL 8 MONTH), 95000.00, 'Taller Premium', 'Revisión completa oficial', 24000
    
    -- Mantenimientos para FGH890 (Mercedes-Benz C200)
    UNION ALL SELECT 'FGH890', DATE_SUB(NOW(), INTERVAL 2 MONTH), 90000.00, 'Taller Premium', 'Servicio oficial Mercedes', 14500
    
    -- Mantenimientos para IJK123 (Audi A3)
    UNION ALL SELECT 'IJK123', DATE_SUB(NOW(), INTERVAL 5 MONTH), 80000.00, 'Taller Premium', 'Cambio de aceite y revisión', 41000
    UNION ALL SELECT 'IJK123', DATE_SUB(NOW(), INTERVAL 12 MONTH), 95000.00, 'Taller Premium', 'Revisión completa oficial', 36000
    
    -- Mantenimientos para LMN456 (Volkswagen Jetta)
    UNION ALL SELECT 'LMN456', DATE_SUB(NOW(), INTERVAL 3 MONTH), 55000.00, 'Taller Central', 'Cambio de aceite y filtros', 31000
    UNION ALL SELECT 'LMN456', DATE_SUB(NOW(), INTERVAL 10 MONTH), 65000.00, 'Taller Central', 'Revisión completa', 27000
    
    -- Mantenimientos para OPQ789 (Chevrolet Malibu)
    UNION ALL SELECT 'OPQ789', DATE_SUB(NOW(), INTERVAL 2 MONTH), 60000.00, 'Taller Este', 'Cambio de aceite', 54000
    UNION ALL SELECT 'OPQ789', DATE_SUB(NOW(), INTERVAL 8 MONTH), 70000.00, 'Taller Este', 'Revisión de frenos y suspensión', 50000
    UNION ALL SELECT 'OPQ789', DATE_SUB(NOW(), INTERVAL 15 MONTH), 65000.00, 'Taller Oeste', 'Cambio de correa de distribución', 45000
    
    -- Mantenimientos para RST012 (Toyota RAV4)
    UNION ALL SELECT 'RST012', DATE_SUB(NOW(), INTERVAL 1 MONTH), 55000.00, 'Taller Norte', 'Primer servicio', 7500
    
    -- Mantenimientos para UVW345 (Honda CR-V)
    UNION ALL SELECT 'UVW345', DATE_SUB(NOW(), INTERVAL 3 MONTH), 60000.00, 'Taller Sur', 'Cambio de aceite y revisión', 24000
    UNION ALL SELECT 'UVW345', DATE_SUB(NOW(), INTERVAL 10 MONTH), 70000.00, 'Taller Sur', 'Revisión completa', 20000
    
    -- Mantenimientos para XYZ678 (Ford Escape)
    UNION ALL SELECT 'XYZ678', DATE_SUB(NOW(), INTERVAL 4 MONTH), 58000.00, 'Taller Central', 'Cambio de aceite y filtros', 37000
    UNION ALL SELECT 'XYZ678', DATE_SUB(NOW(), INTERVAL 12 MONTH), 68000.00, 'Taller Central', 'Revisión completa', 32000
    
    -- Mantenimientos para ABC234 (Nissan Kicks)
    UNION ALL SELECT 'ABC234', DATE_SUB(NOW(), INTERVAL 2 MONTH), 50000.00, 'Taller Oeste', 'Primer servicio', 11500
    
    -- Mantenimientos para DEF567 (Hyundai Creta)
    UNION ALL SELECT 'DEF567', DATE_SUB(NOW(), INTERVAL 3 MONTH), 55000.00, 'Taller Norte', 'Cambio de aceite', 29000
    UNION ALL SELECT 'DEF567', DATE_SUB(NOW(), INTERVAL 10 MONTH), 65000.00, 'Taller Norte', 'Revisión general', 25000
    
    -- Mantenimientos para GHI890 (Peugeot 3008)
    UNION ALL SELECT 'GHI890', DATE_SUB(NOW(), INTERVAL 5 MONTH), 60000.00, 'Taller Este', 'Cambio de aceite y revisión', 39000
    UNION ALL SELECT 'GHI890', DATE_SUB(NOW(), INTERVAL 13 MONTH), 70000.00, 'Taller Este', 'Revisión completa', 34000
    
    -- Mantenimientos para JKL123 (Renault Duster)
    UNION ALL SELECT 'JKL123', DATE_SUB(NOW(), INTERVAL 2 MONTH), 52000.00, 'Taller Sur', 'Cambio de aceite', 49000
    UNION ALL SELECT 'JKL123', DATE_SUB(NOW(), INTERVAL 9 MONTH), 60000.00, 'Taller Sur', 'Revisión de neumáticos y suspensión', 44000
    
    -- Mantenimientos para MNO456 (Toyota Hilux)
    UNION ALL SELECT 'MNO456', DATE_SUB(NOW(), INTERVAL 3 MONTH), 75000.00, 'Taller Central', 'Cambio de aceite y filtros', 34000
    UNION ALL SELECT 'MNO456', DATE_SUB(NOW(), INTERVAL 10 MONTH), 85000.00, 'Taller Central', 'Revisión completa 4x4', 30000
    
    -- Mantenimientos para PQR789 (Ford Ranger)
    UNION ALL SELECT 'PQR789', DATE_SUB(NOW(), INTERVAL 1 MONTH), 70000.00, 'Taller Norte', 'Primer servicio', 17500
    
    -- Mantenimientos para STU012 (Chevrolet S10)
    UNION ALL SELECT 'STU012', DATE_SUB(NOW(), INTERVAL 4 MONTH), 72000.00, 'Taller Este', 'Cambio de aceite y revisión', 41000
    UNION ALL SELECT 'STU012', DATE_SUB(NOW(), INTERVAL 12 MONTH), 82000.00, 'Taller Este', 'Revisión completa', 36000
    
    -- Mantenimientos para VWX345 (Volkswagen Amarok)
    UNION ALL SELECT 'VWX345', DATE_SUB(NOW(), INTERVAL 2 MONTH), 75000.00, 'Taller Oeste', 'Cambio de aceite', 29000
    UNION ALL SELECT 'VWX345', DATE_SUB(NOW(), INTERVAL 9 MONTH), 85000.00, 'Taller Oeste', 'Revisión completa 4x4', 25000
    
    -- Mantenimientos para YZA678 (Nissan Frontier)
    UNION ALL SELECT 'YZA678', DATE_SUB(NOW(), INTERVAL 3 MONTH), 70000.00, 'Taller Sur', 'Cambio de aceite y filtros', 47000
    UNION ALL SELECT 'YZA678', DATE_SUB(NOW(), INTERVAL 11 MONTH), 80000.00, 'Taller Sur', 'Revisión completa', 42000
    
    -- Mantenimientos para TRK001 (Mercedes Actros)
    UNION ALL SELECT 'TRK001', DATE_SUB(NOW(), INTERVAL 2 MONTH), 250000.00, 'Taller Pesados', 'Mantenimiento mayor motor', 177000
    UNION ALL SELECT 'TRK001', DATE_SUB(NOW(), INTERVAL 6 MONTH), 180000.00, 'Taller Pesados', 'Cambio de aceite y filtros', 175000
    UNION ALL SELECT 'TRK001', DATE_SUB(NOW(), INTERVAL 12 MONTH), 220000.00, 'Taller Pesados', 'Revisión de frenos y suspensión', 170000
    
    -- Mantenimientos para TRK002 (Volvo FH16)
    UNION ALL SELECT 'TRK002', DATE_SUB(NOW(), INTERVAL 1 MONTH), 280000.00, 'Taller Pesados', 'Cambio de aceite y revisión', 149000
    UNION ALL SELECT 'TRK002', DATE_SUB(NOW(), INTERVAL 8 MONTH), 200000.00, 'Taller Pesados', 'Mantenimiento preventivo', 145000
    
    -- Mantenimientos para TRK003 (Scania R450)
    UNION ALL SELECT 'TRK003', DATE_SUB(NOW(), INTERVAL 3 MONTH), 260000.00, 'Taller Pesados', 'Revisión completa motor', 198000
    UNION ALL SELECT 'TRK003', DATE_SUB(NOW(), INTERVAL 9 MONTH), 190000.00, 'Taller Pesados', 'Cambio de aceite y filtros', 195000
    UNION ALL SELECT 'TRK003', DATE_SUB(NOW(), INTERVAL 15 MONTH), 240000.00, 'Taller Pesados', 'Revisión de transmisión', 190000
    
    -- Mantenimientos para TRK004 (Iveco Stralis)
    UNION ALL SELECT 'TRK004', DATE_SUB(NOW(), INTERVAL 2 MONTH), 240000.00, 'Taller Pesados', 'Primer servicio mayor', 119000
    
    -- Mantenimientos para TRK005 (MAN TGX)
    UNION ALL SELECT 'TRK005', DATE_SUB(NOW(), INTERVAL 4 MONTH), 270000.00, 'Taller Pesados', 'Cambio de aceite y revisión', 168000
    UNION ALL SELECT 'TRK005', DATE_SUB(NOW(), INTERVAL 11 MONTH), 210000.00, 'Taller Pesados', 'Mantenimiento preventivo', 163000
    
    -- Mantenimientos para TRK006 (Mercedes Atego)
    UNION ALL SELECT 'TRK006', DATE_SUB(NOW(), INTERVAL 3 MONTH), 120000.00, 'Taller Medianos', 'Cambio de aceite y filtros', 94000
    UNION ALL SELECT 'TRK006', DATE_SUB(NOW(), INTERVAL 10 MONTH), 150000.00, 'Taller Medianos', 'Revisión completa', 90000
    
    -- Mantenimientos para TRK007 (Volvo FM)
    UNION ALL SELECT 'TRK007', DATE_SUB(NOW(), INTERVAL 2 MONTH), 130000.00, 'Taller Medianos', 'Cambio de aceite', 109000
    UNION ALL SELECT 'TRK007', DATE_SUB(NOW(), INTERVAL 9 MONTH), 160000.00, 'Taller Medianos', 'Revisión de frenos', 105000
    
    -- Mantenimientos para TRK008 (Scania P320)
    UNION ALL SELECT 'TRK008', DATE_SUB(NOW(), INTERVAL 1 MONTH), 110000.00, 'Taller Medianos', 'Primer servicio', 79000
    
    -- Mantenimientos para TRK009 (Iveco Tector)
    UNION ALL SELECT 'TRK009', DATE_SUB(NOW(), INTERVAL 4 MONTH), 115000.00, 'Taller Medianos', 'Cambio de aceite y revisión', 99000
    UNION ALL SELECT 'TRK009', DATE_SUB(NOW(), INTERVAL 12 MONTH), 140000.00, 'Taller Medianos', 'Revisión completa', 95000
    
    -- Mantenimientos para TRK010 (Ford Cargo)
    UNION ALL SELECT 'TRK010', DATE_SUB(NOW(), INTERVAL 3 MONTH), 100000.00, 'Taller Medianos', 'Cambio de aceite', 84000
    
    -- Mantenimientos para TRK011 (Mercedes Sprinter)
    UNION ALL SELECT 'TRK011', DATE_SUB(NOW(), INTERVAL 2 MONTH), 80000.00, 'Taller Livianos', 'Cambio de aceite y filtros', 129000
    UNION ALL SELECT 'TRK011', DATE_SUB(NOW(), INTERVAL 8 MONTH), 95000.00, 'Taller Livianos', 'Revisión general', 125000
    
    -- Mantenimientos para TRK012 (Volkswagen Delivery)
    UNION ALL SELECT 'TRK012', DATE_SUB(NOW(), INTERVAL 1 MONTH), 75000.00, 'Taller Livianos', 'Primer servicio', 69000
    
    -- Mantenimientos para TRK013 (Fiat Ducato)
    UNION ALL SELECT 'TRK013', DATE_SUB(NOW(), INTERVAL 3 MONTH), 85000.00, 'Taller Livianos', 'Cambio de aceite', 94000
    UNION ALL SELECT 'TRK013', DATE_SUB(NOW(), INTERVAL 10 MONTH), 100000.00, 'Taller Livianos', 'Revisión completa', 90000
    
    -- Mantenimientos para TRK014 (Iveco Daily)
    UNION ALL SELECT 'TRK014', DATE_SUB(NOW(), INTERVAL 2 MONTH), 90000.00, 'Taller Livianos', 'Cambio de aceite y revisión', 79000
    
    -- Mantenimientos para TRK015 (Renault Master)
    UNION ALL SELECT 'TRK015', DATE_SUB(NOW(), INTERVAL 4 MONTH), 82000.00, 'Taller Livianos', 'Cambio de aceite y filtros', 109000
    UNION ALL SELECT 'TRK015', DATE_SUB(NOW(), INTERVAL 11 MONTH), 98000.00, 'Taller Livianos', 'Revisión general', 105000
) as maintenance_data
INNER JOIN `VEHICLE` v ON v.plate = maintenance_data.plate
WHERE NOT EXISTS (
    SELECT 1 FROM maintenance_record mr 
    WHERE mr.vehicle_id = v.id 
    AND mr.maintenance_at = maintenance_data.maintenance_at
    AND ABS(TIMESTAMPDIFF(SECOND, mr.maintenance_at, maintenance_data.maintenance_at)) < 1
);

-- ============================================
-- ACTUALIZAR CAMPOS last_maintenance_* EN VEHICLE
-- ============================================

UPDATE `VEHICLE` v
INNER JOIN (
    SELECT 
        mr1.vehicle_id,
        mr1.maintenance_at as last_maintenance_at,
        mr1.cost as last_maintenance_cost,
        mr1.performed_by as last_maintenance_by
    FROM maintenance_record mr1
    INNER JOIN (
        SELECT vehicle_id, MAX(maintenance_at) as max_date
        FROM maintenance_record
        WHERE deleted = FALSE
        GROUP BY vehicle_id
    ) as max_dates ON mr1.vehicle_id = max_dates.vehicle_id 
        AND mr1.maintenance_at = max_dates.max_date
    WHERE mr1.deleted = FALSE
) as latest_maintenance ON latest_maintenance.vehicle_id = v.id
SET 
    v.last_maintenance_at = latest_maintenance.last_maintenance_at,
    v.last_maintenance_cost = latest_maintenance.last_maintenance_cost,
    v.last_maintenance_by = latest_maintenance.last_maintenance_by,
    v.updated_at = NOW()
WHERE v.deleted = FALSE;
