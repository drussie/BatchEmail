package com.example.email.service.impl;

import com.example.email.model.Client;
import com.example.email.repository.ClientRepository;
import com.example.email.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    @Override
    public Client saveClient(Client client) {
        if (!clientRepository.existsByEmailIgnoreCase(client.getEmail())) {
            throw new RuntimeException("Client with email " + client.getEmail() + " already exists");
        }
        clientRepository.save(client);

        // TODO send email to client


        return client;
    }
}
