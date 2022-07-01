package com.lesonTask.practic.repository;

import com.lesonTask.practic.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    List<Product> priceGreaterThanEqual(Double price);

    List<Product> priceLessThanEqual(Double price);

    List<Product> findByAmount(Integer amount);

    List<Product> findByExpireDate(String expireDate);
}
