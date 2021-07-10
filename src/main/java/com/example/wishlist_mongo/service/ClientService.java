package com.example.wishlist_mongo.service;

import com.example.wishlist_mongo.document.Client;
import com.example.wishlist_mongo.repository.ClientRepository;
import com.example.wishlist_mongo.service.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    // Get All clients
    public List<Client> findAllClients() throws CustomException {
        return clientRepository.findAll();
    }

    //Register new client
    public Client registerClient(Client client) throws CustomException {
        Optional<Client> searchClient = clientRepository.findClientByCpf(client.getCpf());

        if(searchClient.isEmpty()) {
            client.setId(UUID.randomUUID());
            return clientRepository.save(client);
        }

        throw new CustomException("CPF already registered", HttpStatus.BAD_REQUEST);
    }

    //Find client by CPF
    public Client findClientByCPF(String cpf) {
        return clientRepository.findClientByCpf(cpf).orElseThrow(
                () -> new CustomException("User not found", HttpStatus.NOT_FOUND)
        );
    }

    //Update exist client
    public Client updateClient(String cpf, Client client) throws CustomException {
        Optional<Client> searchClient = clientRepository.findClientByCpf(cpf);

        if(searchClient.isPresent()){
            client.setId(searchClient.get().getId());
            return clientRepository.save(client);
        }

        throw new CustomException("User not found", HttpStatus.NOT_FOUND);
    }

    public String removeClient(String cpf) {
        Optional<Client> searchClient = clientRepository.findClientByCpf(cpf);

        if(searchClient.isPresent()){
            clientRepository.deleteClientByCpf(cpf);
            return "Client successfully deleted";
        }

        throw new CustomException("User not found", HttpStatus.NOT_FOUND);
    }
}
