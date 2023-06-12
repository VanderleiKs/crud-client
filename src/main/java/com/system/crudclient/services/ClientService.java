package com.system.crudclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.crudclient.dtos.ClientDTO;
import com.system.crudclient.entities.Client;
import com.system.crudclient.repositories.ClientRepository;
import com.system.crudclient.services.exceptions.DatabaseExceptionService;
import com.system.crudclient.services.exceptions.NotFoundExceptionService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable page) {
        return repository.findAll(page).map(c -> new ClientDTO(c));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id) {
        var client = repository.findById(id).orElseThrow(
                () -> new NotFoundExceptionService("Cliente não encontrado com id " + id));
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {
        var client = new Client();
        dtoToEntity(dto, client);
        client = repository.save(client);
        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try {
            var client = repository.getReferenceById(id);
            dtoToEntity(dto, client);
            repository.save(client);
            return new ClientDTO(client);
        } catch (EntityNotFoundException e) {
            throw new NotFoundExceptionService("Cliente não encontrado com id " + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id))
            throw new NotFoundExceptionService("Cliente não encontrado com id " + id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseExceptionService("Não é possível deletar devido Integridade referencial");
        }
    }

    private void dtoToEntity(ClientDTO dto, Client entity) {
        entity.setName(dto.name());
        entity.setCpf(dto.cpf());
        entity.setIncome(dto.income());
        entity.setBirthDate(dto.birthDate());
        entity.setChildren(dto.children());
    }
}
