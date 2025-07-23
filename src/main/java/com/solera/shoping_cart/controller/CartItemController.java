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

import com.solera.shoping_cart.contracts.ICartItem;
import com.solera.shoping_cart.model.CartItem;


@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final ICartItem cartItemService;

    public CartItemController(ICartItem cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public ResponseEntity<String> saveCartItem(@RequestBody CartItem cartItem) {
        boolean saved = cartItemService.save(cartItem);
        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("CartItem saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("CartItem could not be saved.");
        }
    }


    @GetMapping
    public ResponseEntity<?> getAllCartItems() {
       List<CartItem> cartItems = cartItemService.findAll();
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No cart items were found in the database.");
        } else {
            return ResponseEntity.ok(cartItems);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getCartItemById(@PathVariable Long id) {
        CartItem item = cartItemService.findById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            String message = "Cart item with id " + id + " not found.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItem(@PathVariable Long id) {
        boolean deleted = cartItemService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Cart item with id " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart item with id " + id + " was not found and could not be deleted.");
        }
    }
}
