package com.sqre.app.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.sqre.app.model.Cart;
import com.sqre.app.repository.CartRepository;
import com.sqre.app.vo.CartVO;
import com.sqre.app.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
    public Cart addItemToCart(CartVO cart) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Cart> entity = new HttpEntity<>(headers);

        Status status = restTemplate.exchange(URL + cart.getProduct_id(), HttpMethod.GET, entity, Status.class).getBody();

        if (status == null || Objects.equals(status.getStatus(), "error")) {
            return null;
        }

        Cart saveItem = new Cart();
        saveItem.setProductId(cart.getProduct_id());
        saveItem.setQuantity(cart.getQuantity());

        return cartRepository.save(saveItem);
    }

    @Override
    public List<CartVO> listCartItems() {

        List<Cart> all = cartRepository.findAll();

        if (all.isEmpty()) {
            return Collections.emptyList();
        }

        List<CartVO> cartVOList = new ArrayList<>();

        all.forEach(cart -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity<Cart> entity = new HttpEntity<>(headers);

            Status status = restTemplate.exchange(URL + cart.getProductId(), HttpMethod.GET, entity, Status.class).getBody();
            cartVOList.add(CartVO.builder()
                .product_id(cart.getProductId())
                .description(status.getProductVO().getDescription())
                .quantity(cart.getQuantity())
                .build());
        });

        return cartVOList;
    }

    @Override
    public void removeCartItems() {
        cartRepository.deleteAll();
    }
}
