package com.myhotel.backendtest.exception;

/**
 * Excepción para reglas de pre-validación de negocio
 * (ej: patente duplicada, entidad no encontrada antes de operar).
 * Extiende ServiceException para que el handler la capture en la misma familia.
 *
 * @author Martin Lopez
 */
public class PreValidationException extends ServiceException {

    public PreValidationException(String msg, String code) {
        super(msg, code);
    }

    public PreValidationException(String msg, String code, Throwable cause) {
        super(msg, code, cause);
    }
}
