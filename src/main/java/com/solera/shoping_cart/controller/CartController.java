package com.solera.shoping_cart.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        if (cart.getUser() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot create a cart without assigning it to a user.");
        }

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No carts found.");
        }

        List<Map<String, Object>> cartSummaries = carts.stream().map(cart -> {
            double[] totalPrice = { 0 }; // arreglo para usar en lambda
            int[] totalQuantity = { 0 };

            List<Map<String, Object>> items = cart.getItems().stream().map(item -> {
                double subtotal = item.getQuantity() * item.getProduct().getPrice();
                totalPrice[0] += subtotal;
                totalQuantity[0] += item.getQuantity();

                Map<String, Object> itemData = new LinkedHashMap<>();
                itemData.put("productName", item.getProduct().getName());
                itemData.put("unitPrice", item.getProduct().getPrice());
                itemData.put("quantity", item.getQuantity());
                itemData.put("subtotal", subtotal);
                return itemData;
            }).toList();

            Map<String, Object> cartData = new LinkedHashMap<>();
            cartData.put("cartId", cart.getCartId());
            cartData.put("items", items);
            cartData.put("totalQuantity", totalQuantity[0]);
            cartData.put("totalPrice", totalPrice[0]);

            return cartData;
        }).toList();

        return ResponseEntity.ok(cartSummaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Long id) {
        Cart cart = cartService.findById(id);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart with id " + id + " not found.");
        }

        double[] totalPrice = { 0 };
        int[] totalQuantity = { 0 };

        List<Map<String, Object>> items = cart.getItems().stream().map(item -> {
            double subtotal = item.getQuantity() * item.getProduct().getPrice();
            totalPrice[0] += subtotal;
            totalQuantity[0] += item.getQuantity();

            Map<String, Object> itemData = new LinkedHashMap<>();
            itemData.put("productName", item.getProduct().getName());
            itemData.put("unitPrice", item.getProduct().getPrice());
            itemData.put("quantity", item.getQuantity());
            itemData.put("subtotal", subtotal);
            return itemData;
        }).toList();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("cartId", cart.getCartId());
        result.put("items", items);
        result.put("totalQuantity", totalQuantity[0]);
        result.put("totalPrice", totalPrice[0]);

        return ResponseEntity.ok(result);
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCart(@PathVariable Long id, @RequestBody Cart cart) {
        boolean updated = cartService.update(id, cart);
        if (updated) {
            return ResponseEntity.ok("Cart with id " + id + " was successfully updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cart with id " + id + " was not found and could not be updated.");
        }
    }

}
