package org.feedback.service.payload;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductReview(
        @NotNull(message = "{feedback.products.review.create.product-id_is_null}")
        Integer productId,
        @NotNull(message = "{feedback.products.review.create.rating_is_null}")
        @Min(1)
        @Max(5)
        Integer rating,
        @Size(max = 1000, message = "{feedback.products.review.review_max_1000}")
        String review) {
}
