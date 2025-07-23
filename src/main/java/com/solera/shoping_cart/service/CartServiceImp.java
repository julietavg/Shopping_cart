package com.solera.shoping_cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solera.shoping_cart.contracts.ICart;
import com.solera.shoping_cart.model.Cart;
import com.solera.shoping_cart.repository.CartRepository;

@Service
public class CartServiceImp implements ICart {

    private final CartRepository cartRepository;

    public CartServiceImp(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Boolean save(Cart cart) {
        if (cartRepository.save(cart).getCart_id() != null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Cart findById(Long id) {
        if (cartRepository.existsById(id)) {
            return cartRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Cart> findAll() {
        return (List<Cart>) cartRepository.findAll();
    }

}
