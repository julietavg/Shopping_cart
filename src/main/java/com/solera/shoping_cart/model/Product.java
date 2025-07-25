package com.solera.shoping_cart.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @NotBlank(message = "Product name is requierd.")
    @Size(max = 100, message = "Product name can't exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Description can't exceed 255 characters.")
    private String description;

    @Min(value = 0, message = "Price must be zero or greater.")
    private int price;

}
