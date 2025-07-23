package com.solera.shoping_cart.service;

import java.util.List;

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
        if (cartItemRepository.save(cartItem).getId() != null) {
            return true;
        }
        return false;
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

}
