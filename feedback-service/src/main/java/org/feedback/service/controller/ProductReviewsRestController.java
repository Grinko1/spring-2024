package org.feedback.service.controller;

import lombok.RequiredArgsConstructor;
import org.feedback.service.service.ProductReviewsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback-api/product-reviews")
public class ProductReviewsRestController {
    private final ProductReviewsService reviewsService;
}
