package com.myhotel.backendtest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Respuesta estándar de error.
 * Formato: { status: "ERROR", errors: [{ code, msg }] }.
 *
 * @author Martin Lopez
 */
@Getter
@Setter
public class ErrorResponse {

    private String status;
    private List<ErrorDetail> errors;

    public ErrorResponse() { }

    public ErrorResponse(String status, List<ErrorDetail> errors) {
        this.status = status;
        this.errors = errors;
    }

    @Getter
    @Setter
    public static class ErrorDetail {
        private String code;
        private String msg;

        public ErrorDetail() { }

        public ErrorDetail(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
