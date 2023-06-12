package com.system.crudclient.dtos;

import java.time.LocalDate;

import com.system.crudclient.entities.Client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

public record ClientDTO(
                Long id,
                @NotBlank(message = "Campo nome não pode ser vazio") String name,
                String cpf,
                Double income,
                @PastOrPresent(message = "Data não pode ser futura") LocalDate birthDate,
                Integer children) {

        public ClientDTO(Client c) {
                this(c.getId(),
                                c.getName(),
                                c.getCpf(),
                                c.getIncome(),
                                c.getBirthDate(),
                                c.getChildren());
        }
}
