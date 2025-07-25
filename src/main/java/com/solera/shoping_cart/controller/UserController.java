package com.solera.shoping_cart.controller;

import java.util.ArrayList;
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

import com.solera.shoping_cart.contracts.IUser;
import com.solera.shoping_cart.model.Cart;
import com.solera.shoping_cart.model.CartItem;
import com.solera.shoping_cart.model.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUser userService;

    public UserController(IUser userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        System.out.println("JSON recibido: " + user.getName());
        if (user.getCart() == null) {
            Cart cart = new Cart();
            cart.setUser(user); // vincula el carrito al usuario
            user.setCart(cart); // vincula el usuario al carrito
        }

        boolean saved = userService.save(user);

        if (saved) {
            return ResponseEntity.status(HttpStatus.CREATED).body("User saved successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User could not be saved.");
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No users were found in the database");
        }

        List<Map<String, Object>> userResponses = new ArrayList<>();

        for (User user : users) {
            Map<String, Object> userMap = new LinkedHashMap<>();
            userMap.put("userId", user.getUser_id());
            userMap.put("name", user.getName());
            userMap.put("email", user.getEmail());
            userMap.put("phone", user.getPhone());

            Cart cart = user.getCart();
            if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                List<Map<String, Object>> items = new ArrayList<>();
                double totalPrice = 0;

                for (CartItem item : cart.getItems()) {
                    double itemTotal = item.getQuantity() * item.getProduct().getPrice();
                    totalPrice += itemTotal;

                    Map<String, Object> itemMap = new LinkedHashMap<>();
                    itemMap.put("product", item.getProduct().getName());
                    itemMap.put("quantity", item.getQuantity());
                    itemMap.put("price", item.getProduct().getPrice());
                    itemMap.put("subtotal", itemTotal);
                    items.add(itemMap);
                }

                userMap.put("cartItems", items);
                userMap.put("cartTotal", totalPrice);
            } else {
                userMap.put("cartItems", "No items in cart");
                userMap.put("cartTotal", 0);
            }

            userResponses.add(userMap);
        }

        return ResponseEntity.ok(userResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            String message = "User with id " + id + " not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }

        Map<String, Object> userMap = new LinkedHashMap<>();
        userMap.put("userId", user.getUser_id());
        userMap.put("name", user.getName());
        userMap.put("email", user.getEmail());
        userMap.put("phone", user.getPhone());

        Cart cart = user.getCart();
        if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
            List<Map<String, Object>> items = new ArrayList<>();
            double totalPrice = 0;

            for (CartItem item : cart.getItems()) {
                double itemTotal = item.getQuantity() * item.getProduct().getPrice();
                totalPrice += itemTotal;

                Map<String, Object> itemMap = new LinkedHashMap<>();
                itemMap.put("product", item.getProduct().getName());
                itemMap.put("quantity", item.getQuantity());
                itemMap.put("price", item.getProduct().getPrice());
                itemMap.put("subtotal", itemTotal);
                items.add(itemMap);
            }

            userMap.put("cartItems", items);
            userMap.put("cartTotal", totalPrice);
        } else {
            userMap.put("cartItems", "No items in cart");
            userMap.put("cartTotal", 0);
        }

        return ResponseEntity.ok(userMap);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("User with id " + id + " was successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " was not found and could not be deleted.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        boolean updated = userService.update(id, user);
        if (updated) {
            return ResponseEntity.ok("User with id " + id + " was successfully updated.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with id " + id + " was not found and could not be updated.");
        }
    }

}
