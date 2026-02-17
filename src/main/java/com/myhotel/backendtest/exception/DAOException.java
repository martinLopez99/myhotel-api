package com.myhotel.backendtest.exception;

/**
 * Excepción de la capa DAO.
 * Envuelve errores de acceso a datos con un código y mensaje descriptivo.
 *
 * @author Martin Lopez
 */
public class DAOException extends RuntimeException {

    private final String code;
    private final String msg;

    public DAOException(String msg, String code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public DAOException(String msg, String code, Throwable cause) {
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
