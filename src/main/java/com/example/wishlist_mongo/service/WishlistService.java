package com.example.wishlist_mongo.service;

import com.example.wishlist_mongo.document.Client;
import com.example.wishlist_mongo.document.Product;
import com.example.wishlist_mongo.document.Wishlist;
import com.example.wishlist_mongo.repository.ClientRepository;
import com.example.wishlist_mongo.repository.ProductRepository;
import com.example.wishlist_mongo.repository.WishlistRepository;
import com.example.wishlist_mongo.service.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public List<Wishlist> getAllByClient(String clientCPF) throws CustomException {
        Optional<Client> searchClient = clientRepository.findClientByCpf(clientCPF);

        if(searchClient.isPresent()) {
            return wishListRepository.findWishListByClientCPF(clientCPF);
        }

        throw new CustomException("User not found", HttpStatus.NOT_FOUND);
    }

    public boolean containsProduct(List<Wishlist> userWishList, UUID productId) {
        return userWishList.stream().anyMatch(o -> o.getProductId().equals(productId));
    }

    public String addWish(String clientCPF, UUID productId) throws CustomException {
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
                return "Product successfully added";
            } else {
                throw new CustomException("Wishlist contains 20 items or wish already add", HttpStatus.BAD_REQUEST);
            }
        }

        throw new CustomException("Invalid params", HttpStatus.BAD_REQUEST);
    }

    public String removeWish(String clientCPF, UUID productId) throws CustomException {
        Optional<Client> searchClient = clientRepository.findClientByCpf(clientCPF);
        Optional<Product> searchProduct = productRepository.findProductById(productId);

        if(searchClient.isPresent() && searchProduct.isPresent()) {
            List<Wishlist> userWishList = wishListRepository.findWishListByClientCPF(clientCPF);
            boolean productAlreadyExist = containsProduct(userWishList, productId);

            if(productAlreadyExist){
                wishListRepository.deleteByClientCPFAndProductId(clientCPF, productId);
                return "Produto deletado com sucesso";
            } else {
                throw new CustomException("Product not found", HttpStatus.NOT_FOUND);
            }
        }

        throw new CustomException("Invalid params", HttpStatus.BAD_REQUEST);
    }

    public Product searchWish(String name, String clientCPF) throws CustomException {
        Optional<Client> searchClient = clientRepository.findClientByCpf(clientCPF);
        Optional<Product> searchProduct = productRepository.findProductByName(name);

        if(searchClient.isPresent() && searchProduct.isPresent()) {
            List<Wishlist> userWishList = wishListRepository.findWishListByClientCPF(clientCPF);
            boolean contains = userWishList.stream().anyMatch(product -> product.getProductId().equals(searchProduct.get().getId()));

            if(contains) return searchProduct.get();
        }

        throw new CustomException("Product or Client not found", HttpStatus.NOT_FOUND);
    }
}
