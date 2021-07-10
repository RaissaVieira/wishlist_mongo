package com.example.wishlist_mongo.controller;

import com.example.wishlist_mongo.controller.response.Response;
import com.example.wishlist_mongo.document.Client;
import com.example.wishlist_mongo.service.ClientService;
import com.example.wishlist_mongo.service.exception.CustomException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "Return a list with all the client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns the list clients", response = Response.class),
            @ApiResponse(code = 404, message = "Error on server", response = Response.class)
    })
    @GetMapping()
    public ResponseEntity<?> getAllClients() {
        try{
            return new ResponseEntity<>(clientService.findAllClients(), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(HttpStatus.BAD_REQUEST,"Err on request"), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Find a client by CPF")
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "Returns the user", response = Response.class),
            @ApiResponse(code = 404, message = "Client not found", response = Response.class)
    })
    @GetMapping("/{cpf}")
    public ResponseEntity<?> findClient(@PathVariable String cpf) {
        try{
            return new ResponseEntity<>(clientService.findClientByCPF(cpf), HttpStatus.FOUND);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully registered client", response = Response.class),
            @ApiResponse(code = 400, message = "CPF already registered", response = Response.class)
    })
    @PostMapping()
    public ResponseEntity<?> registerNewClient(@RequestBody Client client) {
        try{
            return new ResponseEntity<>(clientService.registerClient(client), HttpStatus.CREATED);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Update a client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated client", response = Response.class),
            @ApiResponse(code = 404, message = "Client not found", response = Response.class)
    })
    @PutMapping("/{cpf}")
    public ResponseEntity<?> updateClient(@PathVariable String cpf, @RequestBody Client client) {
        try{
            return new ResponseEntity<>(clientService.updateClient(cpf, client), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }

    @ApiOperation(value = "Delete a client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted client", response = Response.class),
            @ApiResponse(code = 404, message = "Client not found", response = Response.class)
    })
    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteClient(@PathVariable String cpf) {
        try{
            return new ResponseEntity<>(clientService.removeClient(cpf), HttpStatus.OK);
        }catch (CustomException e){
            return new ResponseEntity<>(
                    new Response(e.getStatus(), e.getMessage()), e.getStatus());
        }
    }
}