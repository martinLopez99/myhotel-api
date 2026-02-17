package com.myhotel.backendtest.exception;

/**
 * Excepción de la capa Service / Facade.
 * Envuelve errores de negocio y errores inesperados con código y mensaje.
 *
 * @author Martin Lopez
 */
public class ServiceException extends RuntimeException {

    private final String code;
    private final String msg;

    public ServiceException(String msg, String code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg, String code, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
