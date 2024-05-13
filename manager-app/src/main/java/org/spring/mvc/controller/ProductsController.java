package org.spring.mvc.controller;

import lombok.AllArgsConstructor;
import org.spring.mvc.client.BadRequestException;
import org.spring.mvc.client.ProductsRestClient;
import org.spring.mvc.entity.Product;
import org.spring.mvc.payload.NewProductPayload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
@RequestMapping("/catalog/products")
public class ProductsController {
    private final ProductsRestClient productService;


    @GetMapping("/list")
    public String getProducts(Model model) {
        model.addAttribute("products", productService.findAllProducts());
        return "catalog/products/list";
    }

    @GetMapping("/create")
    public String getNewProductPage() {
        return "catalog/products/new_product";
    }

    @PostMapping("/create")
    public String createProduct(NewProductPayload payload, Model model) {
            try{
                Product product = productService.createProduct(payload.title(), payload.details());
                return "redirect:/catalog/products/%d".formatted(product.id());
            }catch (BadRequestException e){
                model.addAttribute("payload", payload);
                model.addAttribute("errors", e.getErrors());
                return "catalog/products/new_product";

            }


    }


}
