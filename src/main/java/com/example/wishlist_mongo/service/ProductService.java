package com.example.wishlist_mongo.service;

import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Get All clients
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    //Register new product
    public Product registerProduct(Product product) {
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    //Find client by CPF
    public Optional<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    //Update exist client
    public Product updateProduct(UUID id, Product product) {
        Optional<Product> searchProduct = productRepository.findProductById(id);

        if(searchProduct.isPresent()){
            product.setId(searchProduct.get().getId());
            return productRepository.save(product);
        }

        return null;
    }

    public String removeProduct(UUID id) {
        Optional<Product> searchProduct = productRepository.findProductById(id);

        if(searchProduct.isPresent()){
            productRepository.deleteById(id);
            return "Produto deletado com sucesso";
        }

        return "Produto não encontrado";
    }
}
