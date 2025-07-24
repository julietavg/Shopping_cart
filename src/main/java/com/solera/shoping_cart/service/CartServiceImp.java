package com.solera.shoping_cart.service;

import java.util.List;
import java.util.Optional;

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
        if (cartRepository.save(cart).getCartId() != null) {
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

    @Override
    public boolean update(Long id, Cart cart) {
    Optional <Cart> optionalCart = cartRepository.findById(id);

    if(optionalCart.isPresent()){
        Cart existingCart = optionalCart.get();

        existingCart.setItems(cart.getItems());
        existingCart.setUser(cart.getUser());

        cartRepository.save(existingCart);
        return true;
    }

    return false;
    }

    

}
