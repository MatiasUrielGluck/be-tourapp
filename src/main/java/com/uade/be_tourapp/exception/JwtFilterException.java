package com.uade.be_tourapp.exception;

import org.hibernate.HibernateException;

public class JwtFilterException extends HibernateException {
    public JwtFilterException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}