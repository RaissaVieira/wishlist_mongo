package com.example.wishlist_mongo.controller;

import com.example.wishlist_mongo.controller.response.Response;
import com.example.wishlist_mongo.dtos.WishlistDTO;
import com.example.wishlist_mongo.service.WishlistService;
import com.example.wishlist_mongo.service.exception.CustomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishListService;

    @ApiOperation(value = "Add a new product on wishlist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully add product on wishlist", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Response.class)
    })
    @PostMapping()
    public ResponseEntity<?> addNewWish(@RequestBody WishlistDTO wishListDTO) {
        try{
            return new ResponseEntity<>(wishListService.addWish(wishListDTO.getClientCPF(), wishListDTO.getProductId()), HttpStatus.FOUND);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Remove a product on wishlist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully remove product on wishlist", response = Response.class),
            @ApiResponse(code = 400, message = "Bad Request", response = Response.class)
    })
    @DeleteMapping()
    public ResponseEntity<?> removeWish(@RequestBody WishlistDTO wishListDTO) {
        try{
            return new ResponseEntity<>(wishListService.removeWish(wishListDTO.getClientCPF(), wishListDTO.getProductId()), HttpStatus.FOUND);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Return client's wishlist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the list product", response = Response.class),
            @ApiResponse(code = 404, message = "Error on server", response = Response.class)
    })
    @GetMapping("/{clientCPF}")
    public ResponseEntity<?> getAllWish(@PathVariable String clientCPF) {
        try{
            return new ResponseEntity<>(wishListService.getAllByClient(clientCPF), HttpStatus.FOUND);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Find a product on wishlist by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the product", response = Response.class),
            @ApiResponse(code = 404, message = "Client or product not found", response = Response.class)
    })
    @GetMapping("/{client_cpf}/search")
    public ResponseEntity<?> searchWish(@RequestParam String name, @PathVariable String client_cpf){
        try{
            return new ResponseEntity<>(wishListService.searchWish(name, client_cpf), HttpStatus.FOUND);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }
}
