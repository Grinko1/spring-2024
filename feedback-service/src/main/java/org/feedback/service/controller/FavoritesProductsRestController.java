package org.feedback.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.feedback.service.entity.FavoriteProduct;
import org.feedback.service.payload.NewFavoriteProductPayload;
import org.feedback.service.service.FavoriteProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback-api/favorite-products")
public class FavoritesProductsRestController {
    private final FavoriteProductsService favoriteService;

    @GetMapping
    public Flux<FavoriteProduct> getFavoriteProduct() {
        System.out.println("Get favorites products "+ favoriteService.findFavoriteProducts());
        return favoriteService.findFavoriteProducts();
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteProduct>> addToFavoriteProduct(@Valid @RequestBody Mono<NewFavoriteProductPayload> payloadMono,
                                                                      UriComponentsBuilder uriComponentsBuilder) {

        return payloadMono
                .flatMap(
                        payload -> favoriteService.addProductToFavorites(payload.productId())
                                .map(favoriteProduct -> ResponseEntity.created(
                                                uriComponentsBuilder.replacePath("/feedback-api/favorite-products/{id}")
                                                        .build(favoriteProduct.getId())
                                        ).body(favoriteProduct)
                                ));
    }

    @DeleteMapping("/productId/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavorite(@PathVariable("productId") int productId) {
        return favoriteService.removeProductFromFavorites(productId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/productId/{productId:\\d+}")
    public Mono<FavoriteProduct> getFavoriteByProductId(@PathVariable("productId") int productId) {
        return favoriteService.findFavoriteProductByProductId(productId);
    }
}
