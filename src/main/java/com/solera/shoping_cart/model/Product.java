package com.solera.shoping_cart.model;

import jakarta.persistence.Entity;
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

    private Long product_id;

    private String name;
    private String description;
    private int price;
    //private String category;  TO-DO we can implement certain category for each product
     


    
}
