package com.example.email.service;

import com.example.email.model.Client;

import java.util.List;

public interface ClientService {
    Client saveClient(Client client);

    List<Client> getAllClients();

//    void sendEmailToClient(Client client);

    boolean sendEmailToClient(Client client);
}
