package org.spring.mvc.repository;

import org.spring.mvc.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository{
    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    public InMemoryProductRepository() {
        IntStream.range(1,4)
                .forEach(i -> products.add(new Product(i, "Product #%d".formatted(i) , "Details for product #%d".formatted(i)) ));
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product save(Product product) {
        product.setId(products.stream().max(Comparator.comparingInt(Product::getId)).map(Product::getId)
                .orElse(0) +1);
        products.add(product);
        return product;
    }
}
