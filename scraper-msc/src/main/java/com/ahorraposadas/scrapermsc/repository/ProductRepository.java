package com.ahorraposadas.scrapermsc.repository;

import java.util.List;
import java.util.Optional;

import com.ahorraposadas.scrapermsc.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByProductUrl(String productUrl);

    List<Product> findByActiveTrue();
}
