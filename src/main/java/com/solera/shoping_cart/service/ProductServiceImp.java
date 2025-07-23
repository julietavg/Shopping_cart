package com.solera.shoping_cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.solera.shoping_cart.contracts.IProduct;
import com.solera.shoping_cart.model.Product;
import com.solera.shoping_cart.repository.ProductRepository;

@Service
public class ProductServiceImp implements IProduct {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Boolean save(Product product) {
        if (productRepository.save(product).getProduct_id() != null) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Product findById(Long id) {
        if (productRepository.existsById(id)) {
            return productRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

}
