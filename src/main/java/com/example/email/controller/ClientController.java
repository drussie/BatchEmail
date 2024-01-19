package com.example.email.controller;

import com.example.email.model.Client;
import com.example.email.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createClient(@RequestBody Client client) {
        Client newClient = clientService.saveClient(client);
        return ResponseEntity.created(URI.create("")).body(
                HttpResponse.builder()
                        .message("Client created successfully")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .data(Map.of("client", newClient))
                        .build()
        );
    }

}
