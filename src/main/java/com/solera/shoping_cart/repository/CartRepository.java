package com.solera.shoping_cart.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solera.shoping_cart.model.Cart;



@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    
}
