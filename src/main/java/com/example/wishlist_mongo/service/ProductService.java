package com.example.wishlist_mongo.service;

import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.repository.ProductRepository;
import com.example.wishlist_mongo.service.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Get All clients
    public List<Product> findAllProduct() throws CustomException {
        return productRepository.findAll();
    }

    //Register new product
    public Product registerProduct(Product product) throws CustomException{
        product.setId(UUID.randomUUID());
        return productRepository.save(product);
    }

    //Find client by CPF
    public Product findProductByName(String name) {
        return productRepository.findProductByName(name).orElseThrow(
                () -> new CustomException("Product not found", HttpStatus.NOT_FOUND)
        );
    }

    //Update exist client
    public Product updateProduct(UUID id, Product product) {
        Optional<Product> searchProduct = productRepository.findProductById(id);

        if(searchProduct.isPresent()){
            product.setId(searchProduct.get().getId());
            return productRepository.save(product);
        }

        throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
    }

    public String removeProduct(UUID id) {
        Optional<Product> searchProduct = productRepository.findProductById(id);

        if(searchProduct.isPresent()){
            productRepository.deleteById(id);
            return "Produt successfully deleted";
        }

        throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
    }
}
