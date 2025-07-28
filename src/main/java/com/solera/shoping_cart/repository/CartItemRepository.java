package com.solera.shoping_cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.solera.shoping_cart.model.CartItem;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    @Query("SELECT c FROM CartItem c WHERE c.cart.cartId = :cartId AND c.product.productId = :productId")
    Optional<CartItem> findByCartAndProduct(@Param("cartId") Long cartId, @Param("productId") Long productId);

    void deleteByCart_CartId(Long cartId);

    boolean existsByProductProductId(Long productId);

}
