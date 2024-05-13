package org.spring.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.rest.entity.Product;
import org.spring.rest.payload.NewProductPayload;
import org.spring.rest.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @GetMapping
    public Iterable<Product> getAll(@RequestParam(name="filter", required = false) String filter) {
        System.out.println("filter is " +filter);
            return productService.findAllProducts(filter);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody NewProductPayload payload,
                                           BindingResult bindingResult,
                                           UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException e) {
                throw e;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Product product = productService.createProduct(payload.title(), payload.details());
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }

    }
}
