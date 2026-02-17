package com.myhotel.backendtest.exception;

import java.util.List;

/**
 * Excepción agregadora de validaciones.
 * Puede contener múltiples mensajes de error (ej: JSR-303 agrupados).
 * Extiende ServiceException para un manejo uniforme en el handler.
 *
 * @author Martin Lopez
 */
public class ValidationException extends ServiceException {

    private final List<String> validationErrors;

    public ValidationException(String msg, String code, List<String> validationErrors) {
        super(msg, code);
        this.validationErrors = validationErrors;
    }

    public ValidationException(String msg, String code) {
        super(msg, code);
        this.validationErrors = List.of(msg);
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
