package com.example.email.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    @Test
    void testGettersAndSetters() {
        UUID id = UUID.randomUUID();
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String email = "testEmail";
        boolean emailSent = false;

        Client client = new Client();
        client.setId(id);
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setEmailSent(false);

        assertEquals(id, client.getId());
        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(email, client.getEmail());
        assertFalse(client.isEmailSent());
    }

    @Test
    void testAllArgsConstructor() {
        UUID id = UUID.randomUUID();
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String email = "testEmail";
        boolean emailSent = true;

        Client client = new Client(id, firstName, lastName, email, emailSent);

        assertEquals(id, client.getId());
        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(email, client.getEmail());
        assertTrue(client.isEmailSent());
    }

    @Test
    void testNoArgsConstructor() {
        Client client = new Client();

        assertNull(client.getId());
        assertNull(client.getFirstName());
        assertNull(client.getLastName());
        assertNull(client.getEmail());
        assertFalse(client.isEmailSent());
    }

    @Test
    void testBuilderPattern() {
        UUID id = UUID.randomUUID();
        String firstName = "testFirstName";
        String lastName = "testLastName";
        String email = "testEmail";
        boolean emailSent = true;

        Client client = Client.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .emailSent(emailSent)
                .build();

        assertEquals(id, client.getId());
        assertEquals(firstName, client.getFirstName());
        assertEquals(lastName, client.getLastName());
        assertEquals(email, client.getEmail());
        assertTrue(client.isEmailSent());
    }
}