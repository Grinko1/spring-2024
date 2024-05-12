package org.spring.mvc.service;

import org.spring.mvc.entity.Product;

import java.util.List;

public interface ProductService {
     List<Product> getAll();

     Product createProduct(String title, String details);
}
