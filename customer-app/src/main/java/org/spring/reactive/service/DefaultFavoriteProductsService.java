package org.spring.reactive.service;

import lombok.RequiredArgsConstructor;
import org.spring.reactive.entity.FavoriteProduct;
import org.spring.reactive.repository.FavoriteProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
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
}
