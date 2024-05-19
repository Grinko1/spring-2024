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
    public Mono<FavoriteProduct> addProductToFavorites(int productId, String userId) {
        return favoriteProductRepository.save( new FavoriteProduct(UUID.randomUUID(), productId, userId));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(int productId,  String userId) {
        return favoriteProductRepository.deleteByProductIdAndUserId(productId, userId);
    }
    public Mono<FavoriteProduct> findFavoriteProductByProductId(int productId, String userId){
        return favoriteProductRepository.findByProductIdAndUserId(productId , userId);
    }

    @Override
    public Flux<FavoriteProduct> findFavoriteProducts(String userId) {
        return favoriteProductRepository.findAllByUserId(userId);
    }

}
