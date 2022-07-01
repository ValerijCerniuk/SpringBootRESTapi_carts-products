package com.lesonTask.practic.service;

import com.lesonTask.practic.model.Product;
import com.lesonTask.practic.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<List<Product>> getAllProduct(){
       final List<Product> products = productRepository.findAll();
        if(products.isEmpty()){
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }


    public ResponseEntity<HttpStatus> save(final Product productToAdd) {
        LOGGER.info("Saving product: " + productToAdd);
        try {
            productRepository.save(productToAdd);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(productRepository.count(),
                HttpStatus.OK);
    }

    public ResponseEntity<Product> findById(final long id) {
        final Optional<Product> _product = productRepository.findById(id);
        return _product.map(product ->
                new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>( HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<Product> getProductByName(final String name) {
        final Optional<Product> _product = productRepository.findByName(name);
        return _product.map(product ->
                new ResponseEntity<>(product, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>( HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<List<Product>> getAllGreaterThanEqual(final Double price) {
        final List<Product> products = productRepository.priceGreaterThanEqual(price);
        if(products.isEmpty()){
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Product>> getAllLessThanEqual(final Double price) {
        final List<Product> products = productRepository.priceLessThanEqual(price);
        if(products.isEmpty()){
            return new ResponseEntity<>(products, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Product>> getProductByAmount(final Integer amount) {
        final List<Product> products = productRepository.findByAmount(amount);
        if(products.isEmpty()){
           return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    public ResponseEntity<List<Product>> getProductByExpireDate(final String expireDate) {
        final List<Product> products = productRepository.findByExpireDate(expireDate);
        if(products.isEmpty()){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }

    public ResponseEntity<HttpStatus> updateProductById(final Long productId, final Product product) {
        Optional<Product> _product = productRepository.findById(productId);
        if (_product.isPresent()) {
            Product productData = _product.get();
            productData.setName(product.getName());
            productData.setPrice(product.getPrice());
            productData.setAmount(product.getAmount());
            productData.setExpireDate(product.getExpireDate());
            productData.setCart(product.getCart());
            productRepository.save(productData);
            LOGGER.info("Product with id: " + productId + " was modified to: " + productData);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    public ResponseEntity<HttpStatus> deleteProductById(final Long productId) {
        if(productRepository.findById(productId).isPresent()) {
            productRepository.deleteById(productId);
            LOGGER.warn("Product with id: " + productId + " deleted...");
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
