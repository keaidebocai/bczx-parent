package top.woaibocai.bczx.order.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.feign.CartFeignClient;
import top.woaibocai.bczx.model.entity.h5.CartInfo;
import top.woaibocai.bczx.model.entity.order.OrderItem;
import top.woaibocai.bczx.model.vo.h5.TradeVo;
import top.woaibocai.bczx.order.service.OrderInfoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-02 16:28
 **/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private CartFeignClient cartFeignClient;
    @Override
    public TradeVo getTrade() {
        List<CartInfo> allCkecked = cartFeignClient.getAllCkecked();
        TradeVo tradeVo = new TradeVo();
        //订单项的集合
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartInfo cartInfo:allCkecked){
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItems.add(orderItem);
        }
        //总价
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem:orderItems){
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));

        }
        tradeVo.setOrderItemList(orderItems);
        tradeVo.setTotalAmount(totalAmount);
        return tradeVo;
    }
}
