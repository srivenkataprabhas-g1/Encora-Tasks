// Creating a custom exception that can be thrown when a user tries to update/delete a customer that doesn't exist
package com.prabhas.exception;

public class NoSuchCustomerExistsException extends RuntimeException {
    private String message;

    public NoSuchCustomerExistsException() {}

    public NoSuchCustomerExistsException(String msg) {
        super(msg);
        this.message = msg;
    }
}