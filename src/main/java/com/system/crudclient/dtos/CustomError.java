package com.system.crudclient.dtos;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public record CustomError(
        Instant timestamp,
        Integer status,
        String error,
        String path,
        @JsonInclude(JsonInclude.Include.NON_NULL) List<FieldMessage> errors) {

}
