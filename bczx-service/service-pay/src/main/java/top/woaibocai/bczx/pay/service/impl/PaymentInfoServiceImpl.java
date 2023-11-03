package top.woaibocai.bczx.pay.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.feign.OrderFeignClient;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.entity.order.OrderItem;
import top.woaibocai.bczx.model.entity.pay.PaymentInfo;
import top.woaibocai.bczx.pay.mapper.PaymentIfnoMapper;
import top.woaibocai.bczx.pay.service.PaymentInfoService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-03 16:34
 **/
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {
    @Resource
    private PaymentIfnoMapper paymentIfnoMapper;
    @Resource
    private OrderFeignClient orderFeignClient;
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        PaymentInfo paymentInfo = paymentIfnoMapper.getByOrderNo(orderNo);
        //2.判断支付记录是否存在
        if (paymentInfo == null){
            //TODO 远程调用订单信息
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            paymentInfo = new PaymentInfo();
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            paymentIfnoMapper.insert(paymentInfo);
        }
        return paymentInfo;
    }
}
