package org.afpparser.parser;

public class InvalidAfpException extends RuntimeException {
    public InvalidAfpException(String msg, Throwable e) {
        super(msg, e);
    }
}
