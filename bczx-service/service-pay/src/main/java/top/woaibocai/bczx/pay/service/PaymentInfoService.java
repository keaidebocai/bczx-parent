package top.woaibocai.bczx.pay.service;

import top.woaibocai.bczx.model.entity.pay.PaymentInfo;

public interface PaymentInfoService {
    //保存支付记录
    PaymentInfo savePaymentInfo(String orderNo);
}
