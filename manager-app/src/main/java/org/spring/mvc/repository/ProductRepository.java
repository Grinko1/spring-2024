package org.spring.mvc.repository;

import org.spring.mvc.entity.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();

    Product save(Product product);
}
