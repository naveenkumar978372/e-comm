package com.sqre.app.service;


import java.util.List;

import com.sqre.app.model.Cart;
import com.sqre.app.vo.CartVO;

public interface CartService {

    void addItemToCart(CartVO cart);

    List<Cart> listCartItems();
}
