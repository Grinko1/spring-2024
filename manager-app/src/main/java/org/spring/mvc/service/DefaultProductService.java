package org.spring.mvc.service;

import lombok.AllArgsConstructor;
import org.spring.mvc.entity.Product;
import org.spring.mvc.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(String title, String details) {
        return productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> getById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void updateProduct(Integer id, String title, String details) {
        this.productRepository.findById(id)
                .ifPresentOrElse(p -> {
                    p.setDetails(details);
                    p.setTitle(title);
                }, () -> {
                    throw new NoSuchElementException();
                });

    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
