package com.sqre.app.controller;

import java.util.List;
import java.util.Objects;

import com.sqre.app.model.Cart;
import com.sqre.app.service.CartService;
import com.sqre.app.vo.CartVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/item")
    public ResponseEntity<Object> addItemToCart(@RequestBody CartVO cart) {

        if (Objects.isNull(cart)) {
            return ResponseEntity.noContent().build();
        }

        Cart cartResponse = cartService.addItemToCart(cart);

        return ResponseEntity.ok(cartResponse);
    }

    @GetMapping(value = "/items")
    public ResponseEntity<List<CartVO>> listCartItems() {
        List<CartVO> carts = cartService.listCartItems();

        if (CollectionUtils.isEmpty(carts)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(carts);
    }

    @PostMapping(value = "/items")
    public ResponseEntity<Object> removeAllCartItems() {


        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }

    @GetMapping(value = "/checkout-value")
    public ResponseEntity<Object> calculateTotalValue() {

        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }
}
