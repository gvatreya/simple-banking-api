package com.gvatreya.finmidbanking.exceptions;

public class ApplicationException extends RuntimeException{

    private String errorCode;

    public ApplicationException(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public ApplicationException(String msg) {
        super(msg);
    }

    public ApplicationException() {
        super();
    }

    public String getErrorCode() { return this.errorCode; }
}
