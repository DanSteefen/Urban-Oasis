package com.urban.oasis.urban.oasis_ecommerce.service.impl;

import com.urban.oasis.urban.oasis_ecommerce.model.CartItem;
import com.urban.oasis.urban.oasis_ecommerce.model.User;
import com.urban.oasis.urban.oasis_ecommerce.repository.CartItemRepository;
import com.urban.oasis.urban.oasis_ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {

        CartItem item = findCartItemById(id);
        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            item.setQty(cartItem.getQty());
            item.setMrpPrice(item.getQty() * item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQty() * item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);
        }
        throw new Exception("You can't update this cart item");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {

        CartItem item = findCartItemById(cartItemId);
        User cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        }
        else throw new Exception("You can't delete this item");
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {

        return cartItemRepository.findById(id).orElseThrow(() -> new Exception("Cart item not found with id " + id));
    }
}
