package com.urban.oasis.urban.oasis_ecommerce.service.impl;

import com.urban.oasis.urban.oasis_ecommerce.model.Cart;
import com.urban.oasis.urban.oasis_ecommerce.model.CartItem;
import com.urban.oasis.urban.oasis_ecommerce.model.Product;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.repository.CartItemRepository;
import com.urban.oasis.urban.oasis_ecommerce.repository.CartRepository;
import com.urban.oasis.urban.oasis_ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) {

        Cart cart = findUserCart(user);
        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(user.getId());
            cartItem.setProduct(product);
            cartItem.setSize(size);
            cartItem.setQty(quantity);

            int totalPrice = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity * product.getMrpPrice());

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }
        return null;
    }

    @Override
    public Cart findUserCart(User user) {

        Cart cart = cartRepository.findUserById(user.getId());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getMrpPrice();
            totalDiscountedPrice += cartItem.getSellingPrice();
            totalItem += cartItem.getQty();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(calculateDiscountPercentage(totalDiscountedPrice, totalPrice));
        cart.setTotalItem(totalItem);
        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {

        if (mrpPrice <= 0){
            return 0;
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount/mrpPrice)*100;
        return (int) discountPercentage;
    }
}
