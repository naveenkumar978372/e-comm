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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @RequestMapping(value = "/item", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addItemToCart(@RequestBody CartVO cart) {

        if (Objects.isNull(cart)) {
            return ResponseEntity.noContent().build();
        }

        cartService.addItemToCart(cart);

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/items", method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> listCartItems() {
        List<Cart> carts = cartService.listCartItems();

        if (CollectionUtils.isEmpty(carts)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(carts);
    }

    @RequestMapping(value = "/items", method = RequestMethod.POST)
    public ResponseEntity<Object> removeAllCartItems() {

        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }

    @RequestMapping(value = "/checkout-value", method = RequestMethod.GET)
    public ResponseEntity<Object> calculateTotalValue() {

        return new ResponseEntity<>(new Object(), HttpStatus.OK);
    }
}
