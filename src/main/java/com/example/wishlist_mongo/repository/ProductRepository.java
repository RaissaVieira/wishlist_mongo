package com.example.wishlist_mongo.repository;

import com.example.wishlist_mongo.document.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends MongoRepository<Product, String> {

    Optional<Product> findProductByName(String name);

    Optional<Product> findProductById(UUID id);

    void deleteById(UUID id);
}
