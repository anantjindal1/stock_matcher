package com.stockmarket.exception;

public class IllegalTradeTypeException extends Exception {
    private static final long serialVersionUID = 2219632870893641452L;

    public IllegalTradeTypeException(String message) {
        super(message);
    }
}
