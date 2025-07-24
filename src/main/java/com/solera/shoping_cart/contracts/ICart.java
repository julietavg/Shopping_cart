package com.solera.shoping_cart.contracts;

import java.util.List;

import com.solera.shoping_cart.model.Cart;

public interface ICart {
    Boolean save(Cart entity);
    Boolean deleteById(Long id);
    Cart findById(Long id);
    List<Cart> findAll();
    boolean update(Long id, Cart cart);
    
}
