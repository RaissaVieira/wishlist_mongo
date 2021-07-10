package com.example.wishlist_mongo.service;

import com.example.wishlist_mongo.document.Client;
import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.document.Wishlist;
import com.example.wishlist_mongo.repository.ClientRepository;
import com.example.wishlist_mongo.repository.ProductRepository;
import com.example.wishlist_mongo.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WishlistService {

    @Autowired
    private WishlistRepository wishListRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<Wishlist> getAllByClient(String clientCPF) {
        return wishListRepository.findWishListByClientCPF(clientCPF);
    }

    public boolean containsProduct(List<Wishlist> userWishList, UUID productId) {
        return userWishList.stream().anyMatch(o -> o.getProductId().equals(productId));
    }

    public String addWish(String clientCPF, UUID productId){
        Optional<Client> searchClient = clientRepository.findClientByCpf(clientCPF);
        Optional<Product> searchProduct = productRepository.findProductById(productId);

        if(searchClient.isPresent() && searchProduct.isPresent()) {

            List<Wishlist> userWishList = wishListRepository.findWishListByClientCPF(clientCPF);
            boolean productAlreadyExist = containsProduct(userWishList, productId);
            if(userWishList.size() <= 20 && !productAlreadyExist) {
                Wishlist wishList = new Wishlist();
                wishList.setId(UUID.randomUUID());
                wishList.setClientCPF(searchClient.get().getCpf());
                wishList.setProductId(searchProduct.get().getId());
                wishListRepository.save(wishList);
                return "Produto adicionado com sucesso";
            } else {
                return "Usuário ultrapassou o limite de 20 itens";
            }
        }
        return "Parametros invalidos";
    }

    public String removeWish(String clientCPF, UUID productId) {
        Optional<Client> searchClient = clientRepository.findClientByCpf(clientCPF);
        Optional<Product> searchProduct = productRepository.findProductById(productId);

        if(searchClient.isPresent() && searchProduct.isPresent()) {
            List<Wishlist> userWishList = wishListRepository.findWishListByClientCPF(clientCPF);
            boolean productAlreadyExist = containsProduct(userWishList, productId);

            if(productAlreadyExist){
                wishListRepository.deleteByClientCPFAndProductId(clientCPF, productId);
                return "Produto deletado com sucesso";
            } else {
                return "Produto não encontrado";
            }
        }
        return "Parametros invalidos";
    }

    public Product searchWish(String name, String clientCPF) {
        Optional<Client> searchClient = clientRepository.findClientByCpf(clientCPF);
        Optional<Product> searchProduct = productRepository.findProductByName(name);

        if(searchClient.isPresent() && searchProduct.isPresent()) {
            List<Wishlist> userWishList = wishListRepository.findWishListByClientCPF(clientCPF);
            boolean contains = userWishList.stream().anyMatch(product -> product.getProductId().equals(searchProduct.get().getId()));

            if(contains) return searchProduct.get();

        }

        return null;
    }
}
