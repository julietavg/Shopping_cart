package com.solera.shoping_cart.contracts;

import java.util.List;

import com.solera.shoping_cart.model.User;

public interface IUser {
    Boolean save(User entity);
    Boolean deleteById(Long id);
    User findById(Long id);
    List<User> findAll();
}
