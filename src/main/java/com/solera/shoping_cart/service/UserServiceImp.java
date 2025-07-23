package com.solera.shoping_cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solera.shoping_cart.contracts.IUser;
import com.solera.shoping_cart.model.User;
import com.solera.shoping_cart.repository.UserRepository;

@Service
public class UserServiceImp implements IUser {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Boolean save(User user) {
        if (userRepository.save(user).getUser_id() != null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public User findById(Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

}
