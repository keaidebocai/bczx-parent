package top.woaibocai.bczx.cart.service;

import top.woaibocai.bczx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    void addToCart(Long skuId, Integer skuNum);

    List<CartInfo> getcartList();

    void deleteCart(Long skuId);

    void checkCart(Long skuId,Integer isChecked);

    void allCheckCart(Integer isChecked);

    void clearCart();

    List<CartInfo> getAllCkecked();

    void deleteChecked();
}
