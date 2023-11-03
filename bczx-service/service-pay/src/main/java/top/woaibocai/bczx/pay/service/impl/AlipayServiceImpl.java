package top.woaibocai.bczx.pay.service.impl;

import com.alibaba.fastjson.JSON;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.model.entity.pay.PaymentInfo;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.pay.service.AlipayService;
import top.woaibocai.bczx.pay.service.PaymentInfoService;
import top.woaibocai.bczx.pay.utils.AlipayProperties;

import java.math.BigDecimal;
import java.util.HashMap;



/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-03 16:33
 **/
@Service
public class AlipayServiceImpl implements AlipayService {
    @Resource
    private PaymentInfoService paymentInfoService;
    @Resource
    private AlipayProperties alipayProperties;
    @Resource
    private AlipayClient alipayClient;
    @Override
    public String submitAlipay(String orderNo) {
        //1.保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);
        //2.调用支付宝服务接口
        //构建需要参数，进行调参
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();

        // 同步回调
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount());
        map.put("total_amount",new BigDecimal("0.01"));
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        // 发送请求
        AlipayTradeWapPayResponse response = null;
        try {
            response = alipayClient.pageExecute(alipayRequest);
        } catch (AlipayApiException e) {
            throw new BoCaiException(ResultCodeEnum.DATA_ERROR);
        }
        if(response.isSuccess()){
            return response.getBody();
        } else {
            throw new BoCaiException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
