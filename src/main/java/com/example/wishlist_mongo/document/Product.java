package com.example.wishlist_mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Document(collection = "product")
public class Product {

    @Id
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
}