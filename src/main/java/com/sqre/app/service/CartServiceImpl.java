package com.sqre.app.service;

import java.util.List;

import com.sqre.app.model.Cart;
import com.sqre.app.repository.CartRepository;
import com.sqre.app.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartServiceImpl implements CartService {

    private static final String URL = "http://e-commerce-api-recruitment.herokuapp.com/";

    @Autowired
    CartRepository cartRepository;

    @Autowired
    RestTemplate restTemplate;

    public CartServiceImpl() {

    }

    @Override
    public void addItemToCart(CartVO cart) {

    }

    @Override
    public List<Cart> listCartItems() {

        return cartRepository.findAll();
    }
}
