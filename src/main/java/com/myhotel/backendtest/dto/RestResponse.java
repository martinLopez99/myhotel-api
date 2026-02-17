package com.myhotel.backendtest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Respuesta estándar para operaciones POST / PUT / DELETE.
 * Formato: { oid, status, message }.
 *
 * @author Martin Lopez
 */
@Getter
@Setter
public class RestResponse {

    private Long oid;
    private String status;
    private String message;

    public RestResponse() { }

    public RestResponse(Long oid, String status, String message) {
        this.oid = oid;
        this.status = status;
        this.message = message;
    }

    /** Factory para respuestas exitosas. */
    public static RestResponse ok(Long oid) {
        return new RestResponse(oid, "OK", null);
    }

    public static RestResponse ok(Long oid, String message) {
        return new RestResponse(oid, "OK", message);
    }
}
