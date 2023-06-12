package com.system.crudclient.services.exceptions;

public class NotFoundExceptionService extends RuntimeException {

    public NotFoundExceptionService(String msg) {
        super(msg);
    }
}
