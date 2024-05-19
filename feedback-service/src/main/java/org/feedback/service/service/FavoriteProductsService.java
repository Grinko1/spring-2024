package org.feedback.service.service;


import org.feedback.service.entity.FavoriteProduct;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface FavoriteProductsService {
    Mono<FavoriteProduct> addProductToFavorites(int ProductId, String userId);
    Mono<Void> removeProductFromFavorites(int productId,  String userId);
    Mono<FavoriteProduct> findFavoriteProductByProductId(int productId, String userId);

    Flux<FavoriteProduct> findFavoriteProducts(String userId);
}
