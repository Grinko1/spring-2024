package org.spring.reactive.client;

import org.spring.reactive.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewsClient {

    Flux<ProductReview> findProductReviews(Integer productId);
    Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review);
}
