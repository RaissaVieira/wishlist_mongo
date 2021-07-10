package com.example.wishlist_mongo.controller;

import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.document.Wishlist;
import com.example.wishlist_mongo.dtos.WishlistDTO;
import com.example.wishlist_mongo.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishListService;

    @PostMapping()
    public String addNewWish(@RequestBody WishlistDTO wishListDTO) {
        return wishListService.addWish(wishListDTO.getClientCPF(), wishListDTO.getProductId());
    }

    @DeleteMapping()
    public String removeWish(@RequestBody WishlistDTO wishListDTO) {
        return wishListService.removeWish(wishListDTO.getClientCPF(), wishListDTO.getProductId());
    }

    @GetMapping("/{clientCPF}")
    public List<Wishlist> getAllWish(@PathVariable String clientCPF) {
        return wishListService.getAllByClient(clientCPF);
    }

    @GetMapping("/{client_cpf}/search")
    public Product searchWish(@RequestParam String name, @PathVariable String client_cpf){
        return wishListService.searchWish(name, client_cpf);
    }
}
