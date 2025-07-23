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
            List<CartItem> allItems = cartItemService.findAll();

            int totalItems = allItems.size();
            double totalPrice = 0;

            // Calculate total price (product price * quantity)
            for (CartItem item : allItems) {
                totalPrice += item.getProduct().getPrice() * item.getQuantity();
            }

            String message = "Inserted " + totalItems + " items and the total price is $" + totalPrice;
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not save the cart item.");
        }
    }


    @GetMapping
    public ResponseEntity<List<CartItem>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        CartItem item = cartItemService.findById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        boolean deleted = cartItemService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
