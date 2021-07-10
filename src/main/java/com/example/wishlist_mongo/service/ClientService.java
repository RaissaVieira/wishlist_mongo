package com.example.wishlist_mongo.service;

import com.example.wishlist_mongo.document.Client;
import com.example.wishlist_mongo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Get All clients
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    //Register new client
    public Client registerClient(Client client) {
        Optional<Client> searchClient = clientRepository.findClientByCpf(client.getCpf());

        if(searchClient.isEmpty()) {
            client.setId(UUID.randomUUID());
            return clientRepository.save(client);
        }

        return null;
    }

    //Find client by CPF
    public Optional<Client> findClientByCPF(String cpf) {
        return clientRepository.findClientByCpf(cpf);
    }

    //Update exist client
    public Client updateClient(String cpf, Client client) {
        Optional<Client> searchClient = clientRepository.findClientByCpf(cpf);

        if(searchClient.isPresent()){
            client.setId(searchClient.get().getId());
            return clientRepository.save(client);
        }

        return null;
    }

    public String removeClient(String cpf) {
        Optional<Client> searchClient = clientRepository.findClientByCpf(cpf);

        if(searchClient.isPresent()){
            clientRepository.deleteClientByCpf(cpf);
            return "Client deletado com sucesso";
        }

        return "Client não encontrado";
    }
}
