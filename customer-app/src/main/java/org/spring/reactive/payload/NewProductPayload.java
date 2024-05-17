package org.spring.reactive.payload;

public record NewProductPayload(Integer productId, Integer rating, String review) {
}
