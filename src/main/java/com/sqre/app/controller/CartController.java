package com.sqre.app.controller;

import java.util.List;
import java.util.Objects;

import com.sqre.app.model.Cart;
import com.sqre.app.service.CartService;
import com.sqre.app.vo.BaseVO;
import com.sqre.app.vo.CartVO;
import com.sqre.app.vo.EmptyCartVO;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "/item")
    public ResponseEntity<BaseVO> addItemToCart(@RequestBody CartVO cart) {

        if (Objects.isNull(cart)) {
            return ResponseEntity.noContent().build();
        }

        Cart cartResponse = cartService.addItemToCart(cart);

        return Objects.isNull(cartResponse) ?
            ResponseEntity.badRequest().body(BaseVO
                .builder()
                .status("error")
                .message("Invalid product id").build()) :
            ResponseEntity.ok(BaseVO
                .builder()
                .status("success")
                .message("Item has been added to cart").build());
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
    public ResponseEntity<BaseVO> removeAllCartItems(@RequestBody EmptyCartVO emptyCartVO) {

        if (!Objects.equals(emptyCartVO.getAction(), "empty_cart")) {
            return ResponseEntity.badRequest().build();
        }

        cartService.removeCartItems();

        BaseVO success = BaseVO.builder()
            .message("All items have been removed from the cart !")
            .status("success")
            .build();

        return ResponseEntity.ok(success);
    }

    @GetMapping(value = "/checkout-value")
    public ResponseEntity<BaseVO> calculateTotalValue(@RequestParam("shipping_postal_code") String shippingPostalCode) {

        Long total = cartService.calculateTotalValue(shippingPostalCode);

        if (total < 0) {
            return ResponseEntity.badRequest().body(BaseVO.builder()
                .message("Invalid postal code, valid ones are 465535 to 465545")
                .status("error").build());
        }

        BaseVO success = BaseVO.builder()
            .message("Total value of your shopping cart is - " + total)
            .status("success").build();

        return ResponseEntity.ok(success);
    }
}
