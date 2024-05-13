package org.spring.rest.service;

import lombok.AllArgsConstructor;
import org.spring.rest.entity.Product;
import org.spring.rest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultProductService implements ProductService {
    private final ProductRepository productRepository;

    public Iterable<Product> findAllProducts(String filter) {
        if(filter != null && !filter.isBlank() ){
            return productRepository.findAllByTitleLikeIgnoreCase("%"+filter +"%");
        }else{
            return productRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Product createProduct(String title, String details) {
        return productRepository.save(new Product(null, title, details));
    }

    @Override
    public Optional<Product> getById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(Integer id, String title, String details) {
        this.productRepository.findById(id)
                .ifPresentOrElse(p -> {
                    p.setDetails(details);
                    p.setTitle(title);;
                }, () -> {
                    throw new NoSuchElementException();
                });

    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}
