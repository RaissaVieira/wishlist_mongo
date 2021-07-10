package com.example.wishlist_mongo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "client")
public class Client {

    private final String name;
    private final String cpf;
    private final String birthday;
    @Id
    private UUID id;
}
