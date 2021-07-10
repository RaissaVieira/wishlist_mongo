package com.example.wishlist_mongo.controller;

import com.example.wishlist_mongo.document.Client;
import com.example.wishlist_mongo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping()
    public List<Client> getAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping("/{cpf}")
    public Optional<Client> findClient(@PathVariable String cpf) {
        return clientService.findClientByCPF(cpf);
    }

    @PostMapping()
    public Client registerNewClient(@RequestBody Client client) {
        return clientService.registerClient(client);
    }

    @PutMapping("/{cpf}")
    public Client updateClient(@PathVariable String cpf, @RequestBody Client client) {
        return clientService.updateClient(cpf, client);
    }

    @DeleteMapping("/{cpf}")
    public String deleteClient(@PathVariable String cpf) {
        return clientService.removeClient(cpf);
    }
}