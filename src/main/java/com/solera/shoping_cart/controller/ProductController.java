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

//FALTA EL MÉTODO UPDATE
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final IProduct productService;

    public ProductController(IProduct productService) {
        this.productService = productService;
    }

   @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody Product product) {
        boolean saved = productService.save(product);
        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Product saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Product could not be saved.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No products were found in the database.");
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            String message = "Product with id " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        boolean deleted = productService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Product with id " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with id " + id + " was not found and could not be deleted.");
        }
    }
}
