package com.example.email.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.example.email.model.Client;
import com.example.email.repository.ClientRepository;
import com.example.email.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
    openMocks(this);
    }

    @Test
    void saveClient() {
        Client client = new Client();
        client.setEmail("testEmail@deadend.com");

        when(clientRepository.existsByEmailIgnoreCase(anyString())).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client savedClient = clientService.saveClient(client);

        assertNotNull(savedClient);
        assertEquals(client.getEmail(), savedClient.getEmail());
        verify(clientRepository).existsByEmailIgnoreCase("testEmail@deadend.com");
        verify(clientRepository).save(client);
    }

    @Test
    void getAllClients() {
        List<Client> clients = Arrays.asList(new Client(), new Client());

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertNotNull(result);
        assertEquals(clients.size(), result.size());
        verify(clientRepository).findAll();
    }

    @Test
    void sendEmailToClient() {
        Client client = new Client();
        client.setEmail("testEmail@deadend.com");

        boolean result = clientService.sendEmailToClient(client);

        assertTrue(result);
        verify(emailService).sendHtmlEmailWithEmbeddedFiles(client.getFirstName(), client.getLastName(), client.getEmail(), null);
        verify(clientRepository).save(client);
    }
}