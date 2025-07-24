package com.solera.shoping_cart.contracts;

import java.util.List;

import com.solera.shoping_cart.model.CartItem;

public interface ICartItem {
    Boolean save(CartItem entity);
    Boolean deleteById(Long id);
    CartItem findById(Long id);
    List<CartItem> findAll();
    boolean update(Long id, CartItem updatedCartItem);
}
