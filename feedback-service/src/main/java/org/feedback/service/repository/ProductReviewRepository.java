package org.feedback.service.repository;


import org.feedback.service.entity.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductReviewRepository {
    Mono<ProductReview> save(ProductReview productReview);

    Flux<ProductReview> findAllReviewsByProductId(int productId);
}
