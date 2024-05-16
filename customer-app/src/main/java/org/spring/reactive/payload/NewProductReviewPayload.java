package org.spring.reactive.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductReviewPayload(
        @NotNull(message = "{customer.products.review.create.rating_is_null}")
        @Min(1)
        @Max(5)
        Integer rating,
        @Size(max = 1000, message = "{customer.products.review.review_max_1000}")
        String review) {
}
