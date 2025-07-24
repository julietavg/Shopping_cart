package com.solera.shoping_cart.mapper;

import com.solera.shoping_cart.dto.CartItemRequestDTO;
import com.solera.shoping_cart.model.Cart;
import com.solera.shoping_cart.model.CartItem;
import com.solera.shoping_cart.model.Product;

public class CartItemMapper {

    public static CartItem fromDTO(CartItemRequestDTO dto, Cart cart, Product product) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(dto.getQuantity());
        return cartItem;
    }
}
