package org.feedback.service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.feedback.service.entity.ProductReview;
import org.feedback.service.payload.NewProductReview;
import org.feedback.service.service.ProductReviewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback-api/product-reviews")
public class ProductReviewsRestController {
    private final ProductReviewsService reviewsService;

    @GetMapping("/productId/{productId:\\d+")
    public Flux<ProductReview> getReviews(@PathVariable("productId") int productId) {
        return reviewsService.findProductReviewsByProductId(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<ProductReview>> createReview(
            @Valid
            @RequestBody Mono<NewProductReview> payloadMono,
            UriComponentsBuilder uriComponentsBuilder) {
        return payloadMono
                .flatMap(payload -> reviewsService.createProductReview(
                        payload.productId(),
                        payload.rating(),
                        payload.review()))
                .map(productReview -> ResponseEntity.created(uriComponentsBuilder.replacePath("/feedback-api/product-reviews/{id}")
                                .build(productReview.getId()))
                        .body(productReview));

    }



}
