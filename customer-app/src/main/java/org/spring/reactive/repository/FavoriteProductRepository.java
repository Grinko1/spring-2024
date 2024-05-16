package org.spring.reactive.repository;

import org.spring.reactive.entity.FavoriteProduct;
import reactor.core.publisher.Mono;

public interface FavoriteProductRepository {
    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    Mono<Void> deleteByProductId(int productId);

    Mono<FavoriteProduct> findByProductId(int productId);
}
