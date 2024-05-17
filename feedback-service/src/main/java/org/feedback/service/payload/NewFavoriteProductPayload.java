package org.feedback.service.payload;

import jakarta.validation.constraints.NotNull;

public record NewFavoriteProductPayload(
        @NotNull(message = "{feedback.products.favorite.create.product-id_is_null}")
        Integer productId) {
}
