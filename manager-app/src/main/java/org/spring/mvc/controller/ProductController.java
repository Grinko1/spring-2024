package org.spring.mvc.controller;

import lombok.AllArgsConstructor;
import org.spring.mvc.entity.Product;
import org.spring.mvc.payload.UpdateProductPayload;
import org.spring.mvc.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/catalog/products/{productId:\\d+}")
public class ProductController {
    private final ProductService productService;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return productService.getById(productId).orElseThrow();
    }
    @GetMapping
    public String getProduct() {


        return "/catalog/products/product";
    }

    @GetMapping("/edit")
    public String getEditPage() {

        return "catalog/products/edit";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute("product") Product product ,UpdateProductPayload payload) {
      productService.updateProduct(product.getId(), payload.title(), payload.details());
       return "redirect:/catalog/products/%d".formatted(product.getId());
    }
    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product ){
        productService.deleteProduct(product.getId());
        return "redirect:/catalog/products/list";
    }
}
