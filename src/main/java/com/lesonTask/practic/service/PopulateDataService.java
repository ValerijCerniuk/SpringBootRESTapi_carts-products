package com.lesonTask.practic.service;

import com.github.javafaker.Faker;
import com.lesonTask.practic.controller.AuthController;
import com.lesonTask.practic.model.*;
import com.lesonTask.practic.payload.request.SignupRequest;
import com.lesonTask.practic.repository.RoleRepository;
import com.lesonTask.practic.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;



@Service
public class PopulateDataService {
    private final Logger LOGGER = LoggerFactory.getLogger(PopulateDataService.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthController authController;

private final PasswordEncoder passwordEncoder;

    private final Faker faker = new Faker();

    public PopulateDataService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void setProductsData(int entityNumber) {
        for (int i = 0; i < entityNumber; i++) {
            final Product productToAdd = Product.builder()
                    .name(faker.food().fruit())
                    .price(faker.number().randomDouble(2, 1, 10))
                    .expireDate(String.valueOf(faker.date().future(10, 2, TimeUnit.DAYS)))
                    .amount(faker.number().numberBetween(1, 20))
                    .build();
            LOGGER.info("Id in setProductsData is: " + i);
            productService.save(productToAdd);
        }
    }

    public void setCartData(int entityNumber) {
        for (int i = 0; i < entityNumber; i++) {
            final Cart cartToAdd = Cart.builder()
                    .ownerName(faker.name().fullName())
                    .ownerEmail(faker.internet().emailAddress())
                    .build();
            LOGGER.info("Id in setCartData is: " + i);
            cartService.save(cartToAdd);
        }
    }

    public void fillCartWithProduct() {
        int countOfProducts = 0;
        int countOfCarts = 0;
        try {
            countOfProducts = Math.toIntExact(productService.count().getBody());
            countOfCarts = Math.toIntExact(cartService.count().getBody());
        } catch (NullPointerException ignored) {
            LOGGER.warn("NullPointerException is ignored in fillCartWithProduct()");
        }

        for (int i = 0; i <= countOfProducts; i++) {
            ResponseEntity<Product> productData = productService.findById((long) i);
            ResponseEntity<Cart> cartData = cartService.findById((long) getRandomNumberInRange(0, countOfCarts));
            Product product = productData.getBody();
            final Cart cart = cartData.getBody();
            if (product != null && cart != null) {
                product.setCart(cart);
                LOGGER.info("Id in fillCartWithProduct is: " + i);
            }
            productService.save(product);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public void setUpRoles(){
        roleRepository.save( Role.builder()
                        .name(ERole.ROLE_USER)
                .build());
        roleRepository.save( Role.builder()
                .name(ERole.ROLE_MODERATOR)
                .build());
        roleRepository.save( Role.builder()
                .name(ERole.ROLE_ADMIN)
                .build());
    }

    public void setUpUsers(){
        Set<String> userRole = new HashSet<>();
        userRole.add(ERole.ROLE_USER.name());

        Set<String> moderatorRole = new HashSet<>();
        moderatorRole.add(ERole.ROLE_MODERATOR.name());
        moderatorRole.add(ERole.ROLE_USER.name());

        Set<String> adminRole = new HashSet<>();
        adminRole.add(ERole.ROLE_ADMIN.name());
        adminRole.add(ERole.ROLE_MODERATOR.name());
        adminRole.add(ERole.ROLE_USER.name());

        SignupRequest signupRequest1 = new SignupRequest();
        signupRequest1.setUsername("user1");
        signupRequest1.setEmail("user1@test.com");
        signupRequest1.setRole(userRole);
        signupRequest1.setPassword(passwordEncoder.encode( ("123456")));


        SignupRequest signupRequest2 = new SignupRequest();
        signupRequest2.setUsername("mod");
        signupRequest2.setEmail("mod@test.com");
        signupRequest2.setRole(moderatorRole);
        signupRequest2.setPassword(passwordEncoder.encode( ("123456")));


        SignupRequest signupRequest3 = new SignupRequest();
        signupRequest3.setUsername("admin");
        signupRequest3.setEmail("admin@test.com");
        signupRequest3.setRole(adminRole);
        signupRequest3.setPassword(passwordEncoder.encode( ("123456")));


    }
}
