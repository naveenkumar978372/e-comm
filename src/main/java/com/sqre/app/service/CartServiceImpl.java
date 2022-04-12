package com.sqre.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.sqre.app.model.Cart;
import com.sqre.app.model.Dictionary;
import com.sqre.app.model.DictionaryValue;
import com.sqre.app.repository.CartRepository;
import com.sqre.app.repository.DictionaryRepository;
import com.sqre.app.repository.DictionaryValueRepository;
import com.sqre.app.vo.CartVO;
import com.sqre.app.vo.DistanceVO;
import com.sqre.app.vo.ProductVO;
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
    DictionaryRepository dictionaryRepository;

    @Autowired
    DictionaryValueRepository dictionaryValueRepository;

    @Autowired
    RestTemplate restTemplate;

    public CartServiceImpl() {

    }

    @Override
    public Cart addItemToCart(CartVO cart) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Cart> entity = new HttpEntity<>(headers);

        ProductVO productVO = restTemplate.exchange(URL + cart.getProductId(), HttpMethod.GET, entity, ProductVO.class).getBody();

        if (productVO == null || Objects.equals(productVO.getStatus(), "error")) {
            return null;
        }

        Cart saveItem = new Cart();
        saveItem.setProductId(cart.getProductId());
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

            ProductVO productVO = restTemplate.exchange(URL + cart.getProductId(), HttpMethod.GET, entity, ProductVO.class).getBody();
            cartVOList.add(CartVO.builder().quantity(cart.getQuantity()).productId(productVO.getId()).build());
        });

        return cartVOList;
    }

    @Override
    public void removeCartItems() {
        cartRepository.deleteAll();
    }

    @Override
    public Long calculateTotalValue(String shippingPostalCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Cart> entity = new HttpEntity<>(headers);

        DistanceVO distanceVO = restTemplate.exchange(URL + "warehouse/distance?"+ shippingPostalCode ,
            HttpMethod.GET, entity, DistanceVO.class).getBody();

        if (distanceVO == null || distanceVO.getDistanceInKilometers() < 0) {
            return 0L;
        }

        List<Cart> allItems = cartRepository.findAll();
        long weight = 0L;
        long price = 0L;
        long totalCost = 0L;

        for (Cart item : allItems) {
            ProductVO productVO = restTemplate.exchange(URL + item.getProductId(), HttpMethod.GET, entity, ProductVO.class).getBody();
            weight += productVO.getWeightInGrams();
            totalCost += productVO.getPrice();
        }


        if (weight < 2) {
            Optional<Dictionary> byId = dictionaryRepository.findById(2L);

            DictionaryValue dctValue = dictionaryValueRepository
                .findByDictionaryDAndDistance(distanceVO.getDistanceInKilometers(), byId.get().getId());
            price = dctValue.getPrice();

        } else if (weight < 5 && weight > 2.01) {
            Optional<Dictionary> byId = dictionaryRepository.findById(5L);

            DictionaryValue dctValue = dictionaryValueRepository
                .findByDictionaryDAndDistance(distanceVO.getDistanceInKilometers(), byId.get().getId());
            price = dctValue.getPrice();
        } else if (weight < 20 && weight > 5.01) {
            Optional<Dictionary> byId = dictionaryRepository.findById(20L);

            DictionaryValue dctValue = dictionaryValueRepository
                .findByDictionaryDAndDistance(distanceVO.getDistanceInKilometers(), byId.get().getId());
            price = dctValue.getPrice();
        } else if (weight > 20.1) {
            Optional<Dictionary> byId = dictionaryRepository.findById(21L);

            DictionaryValue dctValue = dictionaryValueRepository
                .findByDictionaryDAndDistance(distanceVO.getDistanceInKilometers(), byId.get().getId());
            price = dctValue.getPrice();
        }

        return price + totalCost;
    }
}
