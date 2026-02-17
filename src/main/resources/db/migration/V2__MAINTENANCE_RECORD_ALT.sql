CREATE TABLE IF NOT EXISTS maintenance_record (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    vehicle_id BIGINT NOT NULL,
    maintenance_at TIMESTAMP NOT NULL,
    cost DECIMAL(10,2) NOT NULL,
    performed_by VARCHAR(120) NULL,
    notes VARCHAR(255) NULL,
    current_km INT NULL,
    CONSTRAINT fk_maintenance_vehicle
        FOREIGN KEY (vehicle_id) REFERENCES `VEHICLE`(id)
        ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX ix_maintenance_vehicle (vehicle_id),
    INDEX ix_maintenance_vehicle_date (vehicle_id, maintenance_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
