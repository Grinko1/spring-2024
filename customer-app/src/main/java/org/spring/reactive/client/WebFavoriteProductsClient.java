package org.spring.reactive.client;

import lombok.RequiredArgsConstructor;
import org.spring.reactive.entity.FavoriteProduct;
import org.spring.reactive.exceptions.ClientBadRequestException;
import org.spring.reactive.payload.NewFavoriteProductPayload;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class WebFavoriteProductsClient implements FavoriteProductsClient {
    private final WebClient webClient;

    @Override
    public Mono<FavoriteProduct> findFavoriteProductByProductId(int productId) {
        return webClient.get()
                .uri("/feedback-api/favorite-products/productId/{productId}", productId)
                .retrieve()
                .bodyToMono(FavoriteProduct.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Mono<FavoriteProduct> addProductToFavorites(int productId) {
        System.out.println("web client favorite add to favorite " + productId);
        return this.webClient
                .post()
                .uri("/feedback-api/favorite-products")
                .bodyValue(new NewFavoriteProductPayload(productId))
                .retrieve()
                .bodyToMono(FavoriteProduct.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(int productId) {
        return webClient.delete()
                .uri("/feedback-api/favorite-products/productId/{productId}", productId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

    @Override
    public Flux<FavoriteProduct> findFavoriteProducts() {
        return webClient.get()
                .uri("/feedback-api/favorite-products")
                .retrieve()
                .bodyToFlux(FavoriteProduct.class);
    }
}
