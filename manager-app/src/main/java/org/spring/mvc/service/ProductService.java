package org.spring.mvc.service;

import org.spring.mvc.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
     List<Product> getAll();

     Product createProduct(String title, String details);

     Optional<Product> getById(Integer productId);
}
