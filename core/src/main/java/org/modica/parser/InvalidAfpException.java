package org.modica.parser;

public class InvalidAfpException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidAfpException(String msg, Throwable e) {
        super(msg, e);
    }
}
