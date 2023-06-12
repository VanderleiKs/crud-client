package com.system.crudclient.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.crudclient.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
