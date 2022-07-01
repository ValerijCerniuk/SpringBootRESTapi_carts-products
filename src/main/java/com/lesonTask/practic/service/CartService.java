package com.lesonTask.practic.service;

import com.lesonTask.practic.model.Cart;
import com.lesonTask.practic.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final Logger LOGGER = LoggerFactory.getLogger(CartService.class);
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public ResponseEntity<List<Cart>> getAllCart() {
        final List<Cart> carts = cartRepository.findAll();
        if (carts.isEmpty()) {
            return new ResponseEntity<>(carts, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(carts, HttpStatus.FOUND);
        }
    }

    public ResponseEntity<HttpStatus> save(final Cart cartToAdd) {
        LOGGER.info("Saving cart: " + cartToAdd);
        try {
            cartRepository.save(cartToAdd);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(cartRepository.count(), HttpStatus.OK);
    }

    public ResponseEntity<Cart> findById(final long id) {
        Optional<Cart> _cart = cartRepository.findById(id);
        return _cart.map(cart ->
                new ResponseEntity<>(cart, HttpStatus.FOUND)).orElseGet(() ->
                new ResponseEntity<>( HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Cart> getCartByOwnerName(final String ownerName) {
        Optional<Cart> _cart = cartRepository.findByOwnerName(ownerName);
        return _cart.map(cart ->
                new ResponseEntity<>(cart, HttpStatus.FOUND)).orElseGet(() ->
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }


    public ResponseEntity<Cart> getCartByOwnerEmail(final String ownerEmail) {
        Optional<Cart> _cart = cartRepository.findByOwnerEmail(ownerEmail);
        return _cart.map(cart ->
                new ResponseEntity<>(cart, HttpStatus.FOUND)).orElseGet(() ->
                new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<HttpStatus> deleteCartById(final Long cartId) {
        if(cartRepository.findById(cartId).isPresent()) {
            cartRepository.deleteById(cartId);
            LOGGER.warn("Cart with id: " + cartId + " deleted...");
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HttpStatus> updateCart(final Long cartId, final Cart cart) {
        Optional<Cart> _cart = cartRepository.findById(cartId);
        if (_cart.isPresent()) {
            Cart cartData = _cart.get();
            cartData.setOwnerName(cart.getOwnerName());
            cartData.setOwnerEmail(cart.getOwnerEmail());
            cartData.setProducts(cart.getProducts());
            cartRepository.save(cartData);
            LOGGER.info("Cart with id: " + cartId + " was modified to: " + cartData);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

    }
}
