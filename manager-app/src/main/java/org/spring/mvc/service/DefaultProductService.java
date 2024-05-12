package org.spring.mvc.service;

import lombok.AllArgsConstructor;
import org.spring.mvc.entity.Product;
import org.spring.mvc.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService{
    private final ProductRepository productRepository;

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String details) {
        return productRepository.save(new Product(null ,title, details));
    }

    @Override
    public Optional<Product> getById(Integer productId) {
        return productRepository.findById(productId);
    }
}
