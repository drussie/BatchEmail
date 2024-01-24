package com.example.email.controller;

import com.example.email.model.Client;
import com.example.email.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientControllerTest {

    private ClientController clientController;
    private ClientService clientService;
    @BeforeEach
    void setUp() {
        clientService = mock(ClientService.class);
        clientController = new ClientController(clientService);
    }

    @Test
    void createClient() {
        Client client = new Client();

        when(clientService.saveClient(client)).thenReturn(client);

        ResponseEntity<HttpResponse> responseEntity = clientController.createClient(client);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Client created successfully", responseEntity.getBody().getMessage());
        verify(clientService, times(1)).saveClient(any(Client.class));
    }

    @Test
    void sendEmailsToAllClients() {
        List<Client> clients = List.of(new Client(), new Client());

        when(clientService.getAllClients()).thenReturn(clients);

        ResponseEntity<HttpResponse> responseEntity = clientController.sendEmailsToAllClients();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Emails sent successfully", responseEntity.getBody().getMessage());
        verify(clientService, times(clients.size() - 1)).getAllClients();
    }
}