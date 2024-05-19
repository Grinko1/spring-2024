package org.feedback.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.feedback.service.entity.FavoriteProduct;
import org.feedback.service.payload.NewFavoriteProductPayload;
import org.feedback.service.service.FavoriteProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    public Flux<FavoriteProduct> getFavoriteProduct( Mono<JwtAuthenticationToken> authenticationTokenMono) {

        return authenticationTokenMono.flatMapMany(token ->
                favoriteService.findFavoriteProducts(token.getToken().getSubject())
                );
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteProduct>> addToFavoriteProduct(@Valid @RequestBody Mono<NewFavoriteProductPayload> payloadMono,
                                                                      UriComponentsBuilder uriComponentsBuilder,
                                                                      Mono<JwtAuthenticationToken> authenticationTokenMono) {

        return Mono.zip(authenticationTokenMono, payloadMono)
                .flatMap(
                        tuple -> favoriteService.addProductToFavorites(tuple.getT2().productId(), tuple.getT1().getToken().getSubject())
                                .map(favoriteProduct -> ResponseEntity.created(
                                                uriComponentsBuilder.replacePath("/feedback-api/favorite-products/{id}")
                                                        .build(favoriteProduct.getId())
                                        ).body(favoriteProduct)
                                ));
    }

    @DeleteMapping("/productId/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavorite(@PathVariable("productId") int productId,
                                                                Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono.flatMap(token -> favoriteService.removeProductFromFavorites(productId, token.getToken().getSubject()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/productId/{productId:\\d+}")
    public Mono<FavoriteProduct> getFavoriteByProductId(@PathVariable("productId") int productId,
                                                        Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono.flatMap(token -> favoriteService.findFavoriteProductByProductId(productId, token.getToken().getSubject()))  ;
    }
}
