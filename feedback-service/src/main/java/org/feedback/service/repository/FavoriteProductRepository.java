package org.feedback.service.repository;


import org.feedback.service.entity.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductRepository {
    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    Mono<Void> deleteByProductId(int productId);

    Mono<FavoriteProduct> findByProductId(int productId);

    Flux<FavoriteProduct> findFavoriteProducts();
}
