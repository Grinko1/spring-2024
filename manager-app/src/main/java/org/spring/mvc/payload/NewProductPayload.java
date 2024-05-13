package org.spring.mvc.payload;


public record NewProductPayload(
        String title,
        String details) {
}
