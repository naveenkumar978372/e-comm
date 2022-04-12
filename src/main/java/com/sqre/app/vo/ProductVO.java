package com.sqre.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductVO extends BaseVO {

    int id;
    String name;
    long price;
    String description;
    String category;
    String image;
    long discountPercentage;
    long weightInGrams;

    public ProductVO(int id, String name, long price,
                     String description, String category,
                     String image, long discountPercentage,
                     long weightInGrams) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.discountPercentage = discountPercentage;
        this.weightInGrams = weightInGrams;
    }
}
