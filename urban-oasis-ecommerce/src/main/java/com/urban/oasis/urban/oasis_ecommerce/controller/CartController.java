package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.exception.ProductException;
import com.urban.oasis.urban.oasis_ecommerce.model.Cart;
import com.urban.oasis.urban.oasis_ecommerce.model.CartItem;
import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.request.AddItemRequest;
import com.urban.oasis.urban.oasis_ecommerce.response.ApiResponse;
import com.urban.oasis.urban.oasis_ecommerce.service.CartItemService;
import com.urban.oasis.urban.oasis_ecommerce.service.CartService;
import com.urban.oasis.urban.oasis_ecommerce.service.ProductService;
import com.urban.oasis.urban.oasis_ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;
    private final CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(@RequestHeader("Authorization") String jwt,
                                                  @RequestBody AddItemRequest req) throws ProductException, Exception {

        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(req.getProductId());
        CartItem item = cartService.addCartItem(user, product, req.getSize(), req.getQty());

        ApiResponse res = new ApiResponse();
        res.setMessage("Successfully added item to the cart");

        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@PathVariable Long cartItemId,
                                                             @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Successfully added item to the cart");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(@PathVariable Long cartItemId, @RequestBody CartItem cartItem,
                                                          @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        CartItem updateCartItem = null;

        if (cartItem.getQty() >0 ){
            updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        }

        return new ResponseEntity<>(updateCartItem, HttpStatus.ACCEPTED);
    }
}
