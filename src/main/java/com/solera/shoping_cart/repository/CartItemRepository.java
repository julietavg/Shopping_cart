package com.solera.shoping_cart.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solera.shoping_cart.model.CartItem;



@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    
}
