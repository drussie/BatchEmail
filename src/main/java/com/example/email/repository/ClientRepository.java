package com.example.email.repository;

import com.example.email.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByEmailSentFalse();
    Client findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
