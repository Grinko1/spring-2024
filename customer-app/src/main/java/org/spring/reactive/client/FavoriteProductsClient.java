package org.spring.reactive.client;

import org.spring.reactive.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface FavoriteProductsClient {

    Mono<FavoriteProduct> findFavoriteProductByProductId(int productId);

    Mono<FavoriteProduct> addProductToFavorites(int productId);

    Mono<Void> removeProductFromFavorites(int productId);

    Flux<FavoriteProduct> findFavoriteProducts();
}
