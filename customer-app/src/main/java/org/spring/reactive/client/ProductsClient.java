package org.spring.reactive.client;

import org.spring.reactive.entity.Product;
import reactor.core.publisher.Flux;

public interface ProductsClient {

    Flux<Product> findAllProducts(String filter);
}
