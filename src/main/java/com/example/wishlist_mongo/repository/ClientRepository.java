package com.example.wishlist_mongo.repository;

import com.example.wishlist_mongo.document.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {

    Optional<Client> findClientByCpf(String cpf);

    void deleteClientByCpf(String cpf);
}
