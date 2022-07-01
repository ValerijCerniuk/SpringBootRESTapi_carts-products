package com.lesonTask.practic.service;

import com.lesonTask.practic.model.Cart;
import com.lesonTask.practic.model.Product;
import com.lesonTask.practic.repository.CartRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class CartRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    CartRepository cartRepository;

    @Test
    void should_find_all_cart() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("etras2@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart cart3 = Cart.builder()
                .ownerName("Petras#3")
                .ownerEmail("petras3@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart3);
        Iterable<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(3).contains(cart1, cart2, cart3);
    }

    @Test
    public void should_find_no_carts_if_repository_is_empty() {
        Iterable<Cart> carts = cartRepository.findAll();
        assertThat(carts).isEmpty();
    }

    @Test
    void should_store_a_cart() {
        List<Product> products = new ArrayList<>();
        Cart cart = cartRepository.save(new Cart(
                1L, "Petras#1", "petras1@gmail.com", products));
        assertThat(cart).hasFieldOrPropertyWithValue("ownerName", "Petras#1");
        assertThat(cart).hasFieldOrPropertyWithValue("ownerEmail", "petras1@gmail.com");
        assertThat(cart).hasFieldOrPropertyWithValue("products", new ArrayList<>());
    }

    @Test
    void should_count() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("manto@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart cart3 = Cart.builder()
                .ownerName("Petras#3")
                .ownerEmail("petras3@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart3);
        Long count = cartRepository.count();
        assertThat(count).isEqualTo(3L);
    }

    @Test
    void should_find_cart_by_id() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Petras#2")
                .ownerEmail("petras2@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart foundCart = cartRepository.findById(cart2.getId()).get();
        assertThat(foundCart).isEqualTo(cart2);
    }

    @Test
    void should_find_Cart_By_Owner_Name() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("etras2@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart cart3 = Cart.builder()
                .ownerName("Petras#3")
                .ownerEmail("petras3@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart3);
        Cart foundCart = cartRepository.findByOwnerName("Mantas").get();
        assertThat(foundCart).isEqualTo(cart2);
    }

    @Test
    void should_find_Cart_By_Owner_Email() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("manto@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart cart3 = Cart.builder()
                .ownerName("Petras#3")
                .ownerEmail("petras3@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart3);
        Cart foundCart = cartRepository.findByOwnerEmail("manto@gmail.com").get();
        assertThat(foundCart).isEqualTo(cart2);
    }

    @Test
    public void should_delete_all_carts() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("manto@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart cart3 = Cart.builder()
                .ownerName("Petras#3")
                .ownerEmail("petras3@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart3);
        cartRepository.deleteAll();
        assertThat(cartRepository.findAll()).isEmpty();
    }

    @Test
    void should_delete_cart_by_id() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("manto@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart cart3 = Cart.builder()
                .ownerName("Petras#3")
                .ownerEmail("petras3@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart3);
        cartRepository.deleteById(cart2.getId());
        Iterable<Cart> carts = cartRepository.findAll();
        assertThat(carts).hasSize(2).contains(cart1, cart3);
    }

    @Test
    void should_update_cart_by_id() {
        List<Product> products = new ArrayList<>();
        Cart cart1 = Cart.builder()
                .ownerName("Petras#1")
                .ownerEmail("petras1@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart1);
        Cart cart2 = Cart.builder()
                .ownerName("Mantas")
                .ownerEmail("manto@gmail.com")
                .products(products)
                .build();
        entityManager.persist(cart2);
        Cart updatedCart = Cart.builder()
                .ownerName("newPetras")
                .ownerEmail("newPetro@gmal.com")
                .build();
        Cart cart = cartRepository.findById(cart2.getId()).get();
        cart.setOwnerName(updatedCart.getOwnerName());
        cart.setOwnerEmail(updatedCart.getOwnerEmail());

        cartRepository.save(cart);
        Cart checkCart = cartRepository.findById(cart2.getId()).get();

        assertThat(checkCart.getId()).isEqualTo(cart2.getId());
        assertThat(checkCart.getOwnerName()).isEqualTo(updatedCart.getOwnerName());
        assertThat(checkCart.getOwnerEmail()).isEqualTo(updatedCart.getOwnerEmail());
    }
}
