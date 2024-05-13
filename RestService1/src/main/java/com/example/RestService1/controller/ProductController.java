package com.example.RestService1.controller;

import com.example.RestService1.model.Product;
import com.example.RestService1.repository.ProductRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")

public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Получить продукт по идентификатору
    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));
    }

    // Добавить новый продукт
    @PostMapping("/")
    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Обновить существующий продукт
    @PutMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody Product productDetails) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return productRepository.save(product);
    }

    // Удалить продукт по идентификатору
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with id: " + productId));

        productRepository.delete(product);

        return ResponseEntity.ok().build();
    }

}


