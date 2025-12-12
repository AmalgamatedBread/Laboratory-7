package com.Segovia.Lab_7.service;

import com.Segovia.Lab_7.model.Product;
import com.Segovia.Lab_7.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            return Optional.of(productRepository.save(product));
        }
        return Optional.empty();
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
/**package com.Segovia.Lab_7.service;

import com.Segovia.Lab_7.model.Product;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ProductService {
    private final ArrayList<Product> productList = new ArrayList<>(Arrays.asList(
            new Product(1L, "Banana", 10.00),
            new Product(2L, "Apple", 10.00),
            new Product(3L, "Orange", 10.00)
    ));

    private Long nextId = 4L;


    public ArrayList<Product> getAllProducts() {
        return productList;
    }


    public Optional<Product> getProductById(Long id) {
        return productList.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }


    public Product createProduct(Product product) {
        Product newProduct = Product.builder()
                .id(nextId++)
                .name(product.getName())
                .price(product.getPrice())
                .build();
        productList.add(newProduct);
        return newProduct;
    }


    public Optional<Product> updateProduct(Long id, Product productDetails) {
        Optional<Product> existingProduct = getProductById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            return Optional.of(product);
        }
        return Optional.empty();
    }


    public boolean deleteProduct(Long id) {
        return productList.removeIf(product -> product.getId().equals(id));
    }
}**/