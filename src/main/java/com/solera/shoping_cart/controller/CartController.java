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

import com.solera.shoping_cart.contracts.ICart;
import com.solera.shoping_cart.model.Cart;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final ICart cartService;

    public CartController(ICart cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<String> createCart(@RequestBody Cart cart) {
        boolean saved = cartService.save(cart);
        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Cart created successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not create the cart.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCarts() {
        List<Cart> carts = cartService.findAll();
        if (carts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No carts were found in the database.");
        } else {
            return ResponseEntity.ok(carts);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        Cart cart = cartService.findById(id);
        if (cart != null) {
            return ResponseEntity.ok(cart);
        } else {
            String message = "Cart with id " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartById(@PathVariable Long id) {
        boolean deleted = cartService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Cart with id " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart with id " + id + " was not found and could not be deleted.");
        }
    }
}
