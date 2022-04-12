package com.sqre.app.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CartVO extends BaseVO {
    int productId;
    int quantity;

    CartVO(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
