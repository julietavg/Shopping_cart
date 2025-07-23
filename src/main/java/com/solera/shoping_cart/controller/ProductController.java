package com.solera.shoping_cart.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solera.shoping_cart.contracts.IProduct;
import com.solera.shoping_cart.model.Product;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProduct productService;

    public ProductController(IProduct productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        boolean saved = productService.save(product);

        if (saved) {
            List<Product> allProducts = productService.findAll();

            int totalCount = allProducts.size();
            double totalPrice = 0;

            // Calculate the total price of all products
            for (Product p : allProducts) {
                totalPrice += p.getPrice();
            }

            // Create the response message
            String message = "Were inserted " + totalCount + " product and the total price is " + totalPrice;

            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not save the product.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        boolean deleted = productService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
