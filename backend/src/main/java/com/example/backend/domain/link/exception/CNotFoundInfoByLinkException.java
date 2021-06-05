package com.example.backend.domain.link.exception;

public class CNotFoundInfoByLinkException extends RuntimeException{

    public CNotFoundInfoByLinkException(String msg, Throwable t) {
        super(msg, t);
    }

    public CNotFoundInfoByLinkException(String msg) {
        super(msg);
    }

    public CNotFoundInfoByLinkException() {
        super();
    }

}
