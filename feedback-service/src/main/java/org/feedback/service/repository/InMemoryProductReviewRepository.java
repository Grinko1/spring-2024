package org.feedback.service.repository;


import org.feedback.service.entity.ProductReview;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Repository
public class InMemoryProductReviewRepository implements ProductReviewRepository {
    private final List<ProductReview> reviews = Collections.synchronizedList(new LinkedList<>());
    @Override
    public Mono<ProductReview> save(ProductReview productReview) {
        reviews.add(productReview);
        return  Mono.just(productReview) ;
    }

    @Override
    public Flux<ProductReview> findAllReviewsByProductId(int productId) {
        System.out.println(reviews);
        return Flux.fromIterable(reviews )
                .filter(i -> i.getProductId() == productId);
    }
}
