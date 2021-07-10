package com.example.wishlist_mongo.controller;

import com.example.wishlist_mongo.controller.response.Response;
import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.service.ProductService;
import com.example.wishlist_mongo.service.exception.CustomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Return a list with all the products")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the list product", response = Response.class),
            @ApiResponse(code = 404, message = "Error on server", response = Response.class)
    })
    @GetMapping("/list")
    public ResponseEntity<?> getAllProducts() {
        try{
            return new ResponseEntity<>(productService.findAllProduct(), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(HttpStatus.BAD_REQUEST,"Err on request"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Find a product by name")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Returns the product", response = Response.class),
            @ApiResponse(code = 404, message = "Product not found", response = Response.class)
    })
    @GetMapping()
    public ResponseEntity<?> findProduct(@RequestParam String name) {
        try{
            return new ResponseEntity<>(productService.findProductByName(name), HttpStatus.FOUND);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully registered product", response = Response.class),
            @ApiResponse(code = 400, message = "Product already registered", response = Response.class)
    })
    @PostMapping()
    public ResponseEntity<?> registerNewProduct(@RequestBody Product product) {
        try{
            return new ResponseEntity<>(productService.registerProduct(product), HttpStatus.CREATED);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(HttpStatus.BAD_REQUEST,"Err on request"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated product", response = Response.class),
            @ApiResponse(code = 404, message = "Product not found", response = Response.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        try{
            return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted product", response = Response.class),
            @ApiResponse(code = 404, message = "Product not found", response = Response.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        try{
            return new ResponseEntity<>(productService.removeProduct(id), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }
}