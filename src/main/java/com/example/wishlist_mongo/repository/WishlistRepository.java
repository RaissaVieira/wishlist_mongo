package com.example.wishlist_mongo.repository;

import com.example.wishlist_mongo.document.Wishlist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WishlistRepository extends MongoRepository<Wishlist, String>  {

    List<Wishlist> findWishListByClientCPF(String cpf);

    void deleteByClientCPFAndProductId(String cpf, UUID productId);
}
