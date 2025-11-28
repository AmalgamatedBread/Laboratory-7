package com.Segovia.Lab_7.controller;

import com.Segovia.Lab_7.model.Product;
import com.Segovia.Lab_7.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class GraphController {

    private final ProductService productService;

    @QueryMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public Optional<Product> getProduct(@Argument Long id) {
        return productService.getProductById(id);
    }

    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {
        Product product = Product.builder()
                .name(input.name())
                .price(input.price())
                .build();
        return productService.createProduct(product);
    }

    @MutationMapping
    public Optional<Product> updateProduct(@Argument Long id, @Argument ProductInput input) {
        Product productDetails = Product.builder()
                .name(input.name())
                .price(input.price())
                .build();
        return productService.updateProduct(id, productDetails);
    }

    @MutationMapping
    public boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    public record ProductInput(String name, Double price) {}
}