package org.feedback.service.service;


import org.feedback.service.entity.FavoriteProduct;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface FavoriteProductsService {
    Mono<FavoriteProduct> addProductToFavorites(int ProductId);
    Mono<Void> removeProductFromFavorites(int productId);
    Mono<FavoriteProduct> findFavoriteProductByProductId(int productId);

    Flux<FavoriteProduct> findFavoriteProducts();
}
