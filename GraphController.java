package com.Segovia.Lab_7.controller;

import com.Segovia.Lab_7.model.Product;
import com.Segovia.Lab_7.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
public class GraphController {

    @Autowired
    private ProductService productService;

    @QueryMapping
    public ArrayList<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @QueryMapping
    public Product getProduct(@Argument Long id) {
        return productService.getProductById(id).orElse(null);
    }

    @MutationMapping
    public Product createProduct(@Argument ProductInput input) {
        Product product = new Product();
        product.setName(input.name());
        product.setPrice(input.price());
        return productService.createProduct(product);
    }

    @MutationMapping
    public Product updateProduct(@Argument Long id, @Argument ProductInput input) {
        Product productDetails = new Product();
        productDetails.setName(input.name());
        productDetails.setPrice(input.price());
        return productService.updateProduct(id, productDetails).orElse(null);
    }

    @MutationMapping
    public Boolean deleteProduct(@Argument Long id) {
        return productService.deleteProduct(id);
    }

    public record ProductInput(String name, Double price) {}
}