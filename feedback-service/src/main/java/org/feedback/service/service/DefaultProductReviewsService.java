package org.feedback.service.service;

import lombok.RequiredArgsConstructor;
import org.feedback.service.entity.ProductReview;
import org.feedback.service.repository.ProductReviewRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DefaultProductReviewsService implements ProductReviewsService {
    private final ProductReviewRepository reviewRepository;
    @Override
    public Mono<ProductReview> createProductReview(int productId, int rating, String review, String userId) {
        return reviewRepository.save(new ProductReview(UUID.randomUUID(), productId, rating, review, userId));
    }

    @Override
    public Flux<ProductReview> findProductReviewsByProductId(int productId) {
        return reviewRepository.findAllByProductId(productId);
    }
}
