package org.spring.reactive.client;

import lombok.RequiredArgsConstructor;
import org.spring.reactive.entity.Product;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class WebClientProductsClient implements ProductsClient {
    private final WebClient webClient;
    @Override
    public Flux<Product> findAllProducts(String filter) {
        return webClient.get()
                .uri("/api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class)
                ;
    }

    @Override
    public Mono<Product> findProduct(int productId) {
        return webClient.get()
                .uri("/api/products/{productId}", productId)
                .retrieve()
                .bodyToMono(Product.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }
}
