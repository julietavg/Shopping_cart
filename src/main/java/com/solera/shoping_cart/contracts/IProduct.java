package com.solera.shoping_cart.contracts;

import java.util.List;

import com.solera.shoping_cart.model.Product;

public interface IProduct {
    Boolean save(Product entity);
    Boolean deleteById(Long id);
    Product findById(Long id);
    List<Product> findAll();
}
