package org.spring.reactive.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewFavoriteProductPayload(@JsonProperty("productId") int id) {
}
