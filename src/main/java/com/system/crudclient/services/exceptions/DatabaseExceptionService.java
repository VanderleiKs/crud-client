package com.system.crudclient.services.exceptions;

public class DatabaseExceptionService extends RuntimeException {

    public DatabaseExceptionService(String msg) {
        super(msg);
    }

}
