package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.model.Cart;
import com.urban.oasis.urban.oasis_ecommerce.model.CartItem;
import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.User;

public interface CartService {

    public CartItem addCartItem(User user, Product product, String size, int quantity);
    public Cart findUserCart(User user);
}
