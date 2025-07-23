package com.solera.shoping_cart.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.solera.shoping_cart.model.User;



@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    
}
