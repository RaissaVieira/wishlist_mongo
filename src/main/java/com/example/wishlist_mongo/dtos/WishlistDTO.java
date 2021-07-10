package com.example.wishlist_mongo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class WishlistDTO {
    private String clientCPF;
    private UUID productId;
}
