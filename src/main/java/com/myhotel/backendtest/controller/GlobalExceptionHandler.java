package com.myhotel.backendtest.controller;

import com.myhotel.backendtest.dto.ErrorResponse;
import com.myhotel.backendtest.dto.ErrorResponse.ErrorDetail;
import com.myhotel.backendtest.exception.ErrorCode;
import com.myhotel.backendtest.exception.PreValidationException;
import com.myhotel.backendtest.exception.ServiceException;
import com.myhotel.backendtest.exception.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Handler global de excepciones.
 * Captura excepciones de negocio y devuelve respuestas con formato estándar:
 * { status: "ERROR", errors: [{ code, msg }] }
 *
 * @author Martin Lopez
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ── PreValidationException (reglas de negocio) ──────────────────────────

    @ExceptionHandler(PreValidationException.class)
    public ResponseEntity<ErrorResponse> handlePreValidation(PreValidationException ex) {
        log.warn("PreValidationException - code={} msg={}", ex.getCode(), ex.getMsg());

        ErrorDetail detail = new ErrorDetail(ex.getCode(), ex.getMsg());
        ErrorResponse body = new ErrorResponse("ERROR", List.of(detail));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ── ValidationException (múltiples errores de validación) ───────────────

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(ValidationException ex) {
        log.warn("ValidationException - code={} errors={}", ex.getCode(), ex.getValidationErrors());

        List<ErrorDetail> details = ex.getValidationErrors().stream()
                .map(msg -> new ErrorDetail(ex.getCode(), msg))
                .toList();

        ErrorResponse body = new ErrorResponse("ERROR", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ── ServiceException (errores de negocio genéricos) ─────────────────────

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleService(ServiceException ex) {
        log.error("ServiceException - code={} msg={}", ex.getCode(), ex.getMsg(), ex);

        ErrorDetail detail = new ErrorDetail(ex.getCode(), ex.getMsg());
        ErrorResponse body = new ErrorResponse("ERROR", List.of(detail));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ── JSR-303 / @Valid (MethodArgumentNotValidException) ──────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> new ErrorDetail(
                        "VALIDATION_ERROR",
                        fe.getField() + ": " + fe.getDefaultMessage()))
                .toList();

        log.warn("MethodArgumentNotValidException - {} error(s): {}", details.size(), details);

        ErrorResponse body = new ErrorResponse("ERROR", details);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    // ── Exception inesperada (catch-all) ────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Exception inesperada", ex);

        ErrorDetail detail = new ErrorDetail(ErrorCode.UNEXPECTED_ERROR,
                "Error inesperado del servidor");
        ErrorResponse body = new ErrorResponse("ERROR", List.of(detail));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
