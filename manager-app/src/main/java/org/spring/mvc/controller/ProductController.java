package org.spring.mvc.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.spring.mvc.client.BadRequestException;
import org.spring.mvc.client.ProductsRestClient;
import org.spring.mvc.entity.Product;
import org.spring.mvc.payload.UpdateProductPayload;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/catalog/products/{productId:\\d+}")
public class ProductController {
    private final ProductsRestClient productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return productService.getProductById(productId).orElseThrow(() -> new NoSuchElementException
                ("catalog.errors.product.not_found"));
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
    public String edit( @ModelAttribute(value = "product", binding = false) Product product,
                        UpdateProductPayload payload,
                        Model model) {
            try{
                productService.updateProduct(product.id(), payload.title(), payload.details());
                return "redirect:/catalog/products/%d".formatted(product.id());
            }catch (BadRequestException e){
                model.addAttribute("payload", payload);
                model.addAttribute("errors", e.getErrors());
                return "catalog/products/edit";
            }




    }

    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteProduct(product.id());
        return "redirect:/catalog/products/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", messageSource.getMessage(e.getMessage(), new Object[0], locale));
        return "errors/404";
    }
}
