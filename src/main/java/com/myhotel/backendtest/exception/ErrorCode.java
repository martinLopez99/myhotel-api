package com.myhotel.backendtest.exception;

/**
 * Constantes de códigos de error usadas en las capas DAO y Service.
 *
 * @author Martin Lopez
 */
public final class ErrorCode {

    private ErrorCode() { }

    // ── Vehicle ──────────────────────────────────────────────────────────────
    public static final String VEHICLE_NOT_FOUND            = "VEHICLE_NOT_FOUND";
    public static final String VEHICLE_PLATE_ALREADY_EXISTS = "VEHICLE_PLATE_ALREADY_EXISTS";
    public static final String VEHICLE_INVALID_TYPE         = "VEHICLE_INVALID_TYPE";
    public static final String VEHICLE_DELETED              = "VEHICLE_DELETED";

    // ── Maintenance ─────────────────────────────────────────────────────────
    public static final String MAINTENANCE_NOT_FOUND                    = "MAINTENANCE_NOT_FOUND";
    public static final String MAINTENANCE_MILEAGE_LESS_THAN_VEHICLE    = "MAINTENANCE_MILEAGE_LESS_THAN_VEHICLE";
    public static final String MAINTENANCE_VEHICLE_NOT_FOUND            = "MAINTENANCE_VEHICLE_NOT_FOUND";
    public static final String MAINTENANCE_DELETE_ONLY_LATEST_ALLOWED   = "MAINTENANCE_DELETE_ONLY_LATEST_ALLOWED";

    // ── Genéricos ───────────────────────────────────────────────────────────
    public static final String UNEXPECTED_ERROR             = "UNEXPECTED_ERROR";
    public static final String DAO_UNEXPECTED_ERROR         = "DAO_UNEXPECTED_ERROR";
}
