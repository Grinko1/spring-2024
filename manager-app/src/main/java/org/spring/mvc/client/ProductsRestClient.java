package org.spring.mvc.client;

import org.spring.mvc.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductsRestClient {
    List<Product> findAllProducts();
    Optional<Product> getProductById(int id);
    Product createProduct(String title, String details);
    void updateProduct( int id,String title, String details);
    void deleteProduct(int id);

}
