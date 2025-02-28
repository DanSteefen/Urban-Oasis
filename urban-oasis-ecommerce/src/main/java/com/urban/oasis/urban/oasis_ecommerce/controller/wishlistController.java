package com.urban.oasis.urban.oasis_ecommerce.controller;

import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.model.Wishlist;
import com.urban.oasis.urban.oasis_ecommerce.service.ProductService;
import com.urban.oasis.urban.oasis_ecommerce.service.UserService;
import com.urban.oasis.urban.oasis_ecommerce.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlists")
public class wishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Wishlist> createWishlist(@RequestBody User user) {

        Wishlist wishlist = wishlistService.createWishlist(user);
        return ResponseEntity.ok(wishlist);
    }

    @GetMapping()
    public ResponseEntity<Wishlist> getWishlistByUserId(@RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        Wishlist wishlist = wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable Long productId, @RequestHeader("Authorization") String token) throws Exception {

        User user = userService.findUserByJwtToken(token);
        Product product = productService.findProductById(productId);
        Wishlist updateWishlist = wishlistService.addProductToWishlist(user, product);
        return ResponseEntity.ok(updateWishlist);
    }

}
