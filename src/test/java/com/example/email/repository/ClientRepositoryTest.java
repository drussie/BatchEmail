package com.example.email.repository;

import com.example.email.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Client savedClient;

    @BeforeEach
    void setUp() {
        Client client = new Client();
        client.setEmailSent(false);
        client.setFirstName("testFirstName");
        client.setLastName("testLastName");
        client.setEmail("testEmail@example.com");
        savedClient = testEntityManager.persistAndFlush(client);
    }

    @Test
    void findByEmailSentFalse() {
        List<Client> clients = clientRepository.findByEmailSentFalse();

        assertFalse(clients.isEmpty());
        assertEquals(savedClient.isEmailSent(), clients.get(0).isEmailSent());
    }

    @Test
    void findByEmailIgnoreCase() {
        Client foundCLient = clientRepository.findByEmailIgnoreCase(savedClient.getEmail().toUpperCase());

        assertNotNull(foundCLient);
        assertEquals(savedClient.getEmail(), foundCLient.getEmail());
    }

    @Test
    void existsByEmailIgnoreCase() {
        boolean exists = clientRepository.existsByEmailIgnoreCase(savedClient.getEmail().toUpperCase());

        assertTrue(exists);
    }

    @Test
    void existsByEmailIgnoreCaseFalse() {
        boolean exists = clientRepository.existsByEmailIgnoreCase("nonexistantmail@deadend.com");

        assertFalse(exists);
    }
}