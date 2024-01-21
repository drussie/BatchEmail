package com.example.email.service.impl;

import com.example.email.model.Client;
import com.example.email.repository.ClientRepository;
import com.example.email.service.ClientService;
import com.example.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final EmailService emailService;

    public ClientServiceImpl(ClientRepository clientRepository, EmailService emailService) {
        this.clientRepository = clientRepository;
        this.emailService = emailService;
    }
    @Override
    public Client saveClient(Client client) {
        if (clientRepository.existsByEmailIgnoreCase(client.getEmail())) {
            throw new RuntimeException("Client with email " + client.getEmail() + " already exists");
        }
        Client savedClient = clientRepository.save(client);

        // TODO send email to client
//        emailService.sendSimpleMailMessage(client.getFirstName(), client.getLastName(), client.getEmail(), null);
//        emailService.sendMimeMessageWithAttachment(client.getFirstName(), client.getLastName(), client.getEmail(), null);
//        emailService.sendMimeMessageWithEmbeddedFiles(client.getFirstName(), client.getLastName(), client.getEmail(), null);
        emailService.sendHtmlEmail(client.getFirstName(), client.getLastName(), client.getEmail(), null);

        return savedClient;
    }
}
