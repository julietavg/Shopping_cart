package com.solera.shoping_cart.controller;

import java.util.List;

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

import com.solera.shoping_cart.contracts.ICartItem;
import com.solera.shoping_cart.dto.CartItemRequestDTO;
import com.solera.shoping_cart.mapper.CartItemMapper;
import com.solera.shoping_cart.model.Cart;
import com.solera.shoping_cart.model.CartItem;
import com.solera.shoping_cart.model.Product;
import com.solera.shoping_cart.repository.CartRepository;
import com.solera.shoping_cart.repository.ProductRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final ICartItem cartItemService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartItemController(ICartItem cartItemService,
            ProductRepository productRepository,
            CartRepository cartRepository) {
        this.cartItemService = cartItemService;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @PostMapping
    public ResponseEntity<String> saveCartItem(@Valid @RequestBody CartItemRequestDTO dto) {
        // Buscar el carrito
        Cart cart = cartRepository.findById(dto.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found.");
        }

        // Buscar el producto
        Product product = productRepository.findById(dto.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        // Crear el CartItem con el mapper
        CartItem item = CartItemMapper.fromDTO(dto, cart, product);

        boolean saved = cartItemService.save(item);

        if (saved) {
            int totalQuantity = item.getQuantity();
            double totalPrice = totalQuantity * product.getPrice();
            String result = "Inserted " + totalQuantity + " items. Total price: $" + totalPrice;
            return ResponseEntity.status(HttpStatus.CREATED).body(result);

        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cart item could not be saved.");
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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCartItem(@Valid @PathVariable Long id, @RequestBody CartItemRequestDTO dto) {
        Cart cart = cartRepository.findById(dto.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found.");
        }

        Product product = productRepository.findById(dto.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        CartItem updatedItem = CartItemMapper.fromDTO(dto, cart, product);

        boolean updated = cartItemService.update(id, updatedItem);

        if (updated) {
            return ResponseEntity.ok("Cart item updated successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.");
        }
    }

    @DeleteMapping("/cart/{cartId}/items")
    public ResponseEntity<String> deleteAllItemsByCart(@PathVariable Long cartId) {
        boolean deleted = cartItemService.deleteAllByCartId(cartId);
        if (deleted) {
            return ResponseEntity.ok("All items from cart " + cartId + " were successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No items found for cart " + cartId + ".");
        }
    }

}
