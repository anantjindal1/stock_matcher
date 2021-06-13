package com.stockmarket.exception;

public class OrderFormatException extends Exception {
    private static final long serialVersionUID = 2219632870893641452L;

    public OrderFormatException(String message) {
        super(message);
    }
}
