package com.solera.shoping_cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {

    @NotNull(message = "Cart ID is required")
    private Long cartId;

    @NotNull(message = "Cart ID is required")
    private Long productId;
    
     @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}
