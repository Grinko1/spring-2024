package org.spring.reactive.payload;


public record NewProductReviewPayload(
        Integer rating,
        String review) {
}
