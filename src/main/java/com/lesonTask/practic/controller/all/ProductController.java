package com.lesonTask.practic.controller.all;

import com.lesonTask.practic.model.Product;
import com.lesonTask.practic.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@Controller
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /**
     * Admin endpoint
     */
    @DeleteMapping("/admin/products")
    public ResponseEntity<HttpStatus> deleteProductById(@PathVariable Long productId){
        return productService.deleteProductById(productId);
    }

    /**
     * Moderator endpoint
     */
    @PutMapping("/mod/products/{productId}")
    public ResponseEntity<HttpStatus> updateProductById(@PathVariable("productId") Long productId,
                                                        @Valid @RequestBody Product product){
        return productService.updateProductById(productId, product);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addProduct(@Valid @RequestBody Product product){
        return productService.save(product);
    }

    /**
     * User endpoints
     */
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct() {
        return productService.getAllProduct();
    }

    @GetMapping("/products/find-by/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable("productId") Long productId) {
        return productService.findById(productId);
    }

    @GetMapping("/products/find-by/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable("name") String name) {
        return productService.getProductByName(name);
    }

    @GetMapping("/products/find-by/{amount}")
    public ResponseEntity<List<Product>> getProductByAmount(@PathVariable("amount") Integer amount) {
        return productService.getProductByAmount(amount);
    }

    @GetMapping("/products/find-by/{expireDate}")
    public ResponseEntity<List<Product>> getProductByExpireDate(@PathVariable("expireDate") String expireDate) {
        return productService.getProductByExpireDate(expireDate);
    }

    @GetMapping("/products/find-by/greater/{price}")
    public ResponseEntity<List<Product>> getAllGreaterThan(@PathVariable Double price) {
        return productService.getAllGreaterThanEqual(price);
    }

    @GetMapping("/products/find-by/less/{price}")
    public ResponseEntity<List<Product>> getAllLessThanEqual(@PathVariable Double price) {
        return productService.getAllLessThanEqual(price);
    }


}
