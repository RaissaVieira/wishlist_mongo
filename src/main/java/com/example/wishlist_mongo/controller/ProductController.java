package com.example.wishlist_mongo.controller;

import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> getAllProducts() {
        return productService.findAllProduct();
    }

    @GetMapping()
    public Optional<Product> findProduct(@RequestParam String name) {
        return productService.findProductByName(name);
    }

    @PostMapping()
    public Product registerNewProduct(@RequestBody Product product) {
        return productService.registerProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable UUID id) {
        return productService.removeProduct(id);
    }
}