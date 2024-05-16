package org.feedback.service.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.feedback.service.entity.FavoriteProduct;
import org.feedback.service.repository.FavoriteProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class DefaultFavoriteProductsService implements FavoriteProductsService {
    private final FavoriteProductRepository favoriteProductRepository;
    @Override
    public Mono<FavoriteProduct> addProductToFavorites(int productId) {
        return favoriteProductRepository.save( new FavoriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(int productId) {
        return favoriteProductRepository.deleteByProductId(productId);
    }
    public Mono<FavoriteProduct> findFavoriteProductByProductId(int productId){
        return favoriteProductRepository.findByProductId(productId);
    }

    @Override
    public Flux<FavoriteProduct> findFavoriteProducts() {
        return favoriteProductRepository.findFavoriteProducts();
    }

}
