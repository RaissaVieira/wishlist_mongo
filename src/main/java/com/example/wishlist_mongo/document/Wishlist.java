package com.example.wishlist_mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "wishlist")
public class Wishlist {

    @Id
    private UUID id;
    private String clientCPF;
    private UUID productId;
}
