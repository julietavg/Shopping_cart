package com.solera.shoping_cart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.solera.shoping_cart.contracts.ICartItem;
import com.solera.shoping_cart.model.CartItem;
import com.solera.shoping_cart.repository.CartItemRepository;

@Service
public class CartItemServiceImp implements ICartItem {

    private final CartItemRepository cartItemRepository;

    public CartItemServiceImp(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Boolean save(CartItem cartItem) {
        Optional<CartItem> exists = cartItemRepository.findByCartAndProduct(
                cartItem.getCart().getCartId(),
                cartItem.getProduct().getProductId());
        if (exists.isPresent()) {
            CartItem cartFound = exists.get();
            cartFound.setQuantity(cartFound.getQuantity() + cartItem.getQuantity());
            cartItemRepository.save(cartFound);
        } else {
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (cartItemRepository.existsById(id)) {
            cartItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public CartItem findById(Long id) {
        if (cartItemRepository.existsById(id)) {
            return cartItemRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<CartItem> findAll() {
        return (List<CartItem>) cartItemRepository.findAll();
    }

    @Override
    public boolean update(Long id, CartItem updatedItem) {
        Optional<CartItem> optional = cartItemRepository.findById(id);

        if (optional.isPresent()) {
            CartItem existing = optional.get();
            existing.setQuantity(updatedItem.getQuantity());
            cartItemRepository.save(existing);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAllByCartId(Long cartId) {
        try {
            cartItemRepository.deleteByCart_CartId(cartId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
