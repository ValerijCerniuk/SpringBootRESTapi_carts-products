package com.lesonTask.practic.service;


import com.lesonTask.practic.model.Product;
import com.lesonTask.practic.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    TestEntityManager entityManager;
    @Autowired
    ProductRepository productRepository;

    @Test
    void should_find_all_product() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(10.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        Iterable<Product> carts = productRepository.findAll();
        assertThat(carts).hasSize(3).contains(product1, product2, product3);
    }

    @Test
    public void should_find_no_carts_if_repository_is_empty() {
        Iterable<Product> products = productRepository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    void should_store_a_product() {
        Product product = productRepository.save(Product.builder()
                .name("Product")
                .price(10.10)
                .expireDate("20.10.11")
                .amount(10)
                .build()
        );
        assertThat(product).hasFieldOrPropertyWithValue("name", "Product");
        assertThat(product).hasFieldOrPropertyWithValue("price", 10.10);
        assertThat(product).hasFieldOrPropertyWithValue("expireDate", "20.10.11");
        assertThat(product).hasFieldOrPropertyWithValue("amount", 10);
    }

    @Test
    void should_count() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(10.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        Long count = productRepository.count();
        assertThat(count).isEqualTo(3L);
    }


    @Test
    void should_find_product_by_id() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(10.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product foundProduct = productRepository.findById(product2.getId()).get();
        assertThat(foundProduct).isEqualTo(product2);

    }

    @Test
    void should_find_product_by_name() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(10.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        Product foundProduct = productRepository.findByName("Product2").get();
        assertThat(foundProduct).isEqualTo(product2);
    }

    @Test
    void should_find_product_by_price_greaterThanEqual() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(5.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        Iterable<Product> carts = productRepository.priceGreaterThanEqual(20.10);
        assertThat(carts).hasSize(2).contains(product2, product3);
    }

    @Test
    void should_find_product_by_price_lessThanEqual() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(5.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        Iterable<Product> carts = productRepository.priceLessThanEqual(20.10);
        assertThat(carts).hasSize(2).contains(product1, product2);
    }

    @Test
    void should_find_product_by_amount() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(5.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(20)
                .build();
        entityManager.persist(product3);
        Iterable<Product> carts = productRepository.findByAmount(20);
        assertThat(carts).hasSize(2).contains(product2, product3);
    }

    @Test
    void should_find_product_by_expireDate() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(5.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.13")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(20)
                .build();
        entityManager.persist(product3);
        Iterable<Product> carts = productRepository.findByExpireDate("20.10.13");
        assertThat(carts).hasSize(2).contains(product2, product3);
    }

    @Test
    void should_update_product_by_id() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(10.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);

        Product updatedProduct = Product.builder()
                .name("Product2.1")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();

        Product product = productRepository.findById(product2.getId()).get();
        product.setName(updatedProduct.getName());
        product.setPrice(updatedProduct.getPrice());
        product.setExpireDate(updatedProduct.getExpireDate());
        product.setAmount(updatedProduct.getAmount());

        productRepository.save(product);
        Product checkProduct = productRepository.findById(product2.getId()).get();

        assertThat(checkProduct.getId()).isEqualTo(product2.getId());
        assertThat(checkProduct.getName()).isEqualTo(updatedProduct.getName());
        assertThat(checkProduct.getPrice()).isEqualTo(updatedProduct.getPrice());
        assertThat(checkProduct.getExpireDate()).isEqualTo(updatedProduct.getExpireDate());
        assertThat(checkProduct.getAmount()).isEqualTo(updatedProduct.getAmount());
    }

    @Test
    void should_delete_product_by_Id() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(5.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        productRepository.deleteById(product2.getId());
        Iterable<Product> products = productRepository.findAll();
        assertThat(products).hasSize(2).contains(product1, product3);
    }

    @Test
    void should_delete_all_product() {
        Product product1 = Product.builder()
                .name("Product1")
                .price(5.10)
                .expireDate("20.10.11`")
                .amount(10)
                .build();
        entityManager.persist(product1);
        Product product2 = Product.builder()
                .name("Product2")
                .price(20.10)
                .expireDate("20.10.12")
                .amount(20)
                .build();
        entityManager.persist(product2);
        Product product3 = Product.builder()
                .name("Product3")
                .price(30.10)
                .expireDate("20.10.13")
                .amount(30)
                .build();
        entityManager.persist(product3);
        productRepository.deleteAll();
        assertThat(productRepository.findAll()).isEmpty();
    }
}

