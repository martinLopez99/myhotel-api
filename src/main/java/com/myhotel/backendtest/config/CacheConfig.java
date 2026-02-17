package com.myhotel.backendtest.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de cache en memoria (ConcurrentMapCacheManager).
 * Sin infraestructura externa (Redis, etc.).
 *
 * @author Martin Lopez
 */
@Configuration
@EnableCaching
public class CacheConfig {

    public static final String VEHICLE_BY_ID = "vehicleById";
    public static final String VEHICLE_BY_PLATE = "vehicleByPlate";
    public static final String LATEST_MAINTENANCE_BY_VEHICLE = "latestMaintenanceByVehicleId";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(
                VEHICLE_BY_ID,
                VEHICLE_BY_PLATE,
                LATEST_MAINTENANCE_BY_VEHICLE
        );
    }
}
