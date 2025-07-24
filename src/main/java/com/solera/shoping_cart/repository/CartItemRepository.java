package com.solera.shoping_cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solera.shoping_cart.model.CartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.cart.cart_id = :cartId AND c.product.product_id = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
