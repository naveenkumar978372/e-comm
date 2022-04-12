package com.sqre.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductVO {

    Integer id;
    String name;
    Long price;
    String description;
    String category;
    String image;
    Long discount_percentage;
    Long weight_in_grams;


    /*{
        "status": "success",
        "product": {
        "id": 100,
            "name": "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            "price": 109.95,
            "description": "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            "category": "men's clothing",
            "image": "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
            "discount_percentage": 3.2,
            "weight_in_grams": 670
    }
    }*/
}
