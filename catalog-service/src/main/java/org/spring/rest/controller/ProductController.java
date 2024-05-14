package org.spring.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.spring.rest.entity.Product;
import org.spring.rest.payload.UpdateProductPayload;
import org.spring.rest.service.ProductService;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products/{productId:\\d+}")
public class ProductController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") Integer productId) {
        return productService.getById(productId).orElseThrow(() -> new NoSuchElementException("catalog.errors.product.not_found"));
    }

    @GetMapping
    public Product getById(@ModelAttribute("product") Product product) {
        System.out.println("api get product");
        return product;
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Integer productId,
                                           @Valid @RequestBody UpdateProductPayload payload,
                                           BindingResult bindingResult
    ) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException e) {
                throw e;
            } else {
                throw new BindException((bindingResult));
            }
        } else {
            productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteProduct(product.getId());
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElement(NoSuchElementException e, Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        messageSource.getMessage(e.getMessage(), new Object[0], "catalog.errors.product.not_found", locale)
                ));
    }
}
