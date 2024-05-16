package org.spring.reactive.service;

import lombok.RequiredArgsConstructor;
import org.spring.reactive.entity.ProductReview;
import org.spring.reactive.repository.ProductReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DefaultProductReviewsService implements ProductReviewsService {
    private final ProductReviewRepository reviewRepository;
    @Override
    public Mono<ProductReview> createProductReview(int productId, int rating, String review) {
        return reviewRepository.save(new ProductReview(UUID.randomUUID(), productId, rating, review));
    }

    @Override
    public Flux<ProductReview> findProductReviewsByProductId(int productId) {
        return reviewRepository.findAllReviewsByProductId(productId);
    }
}
