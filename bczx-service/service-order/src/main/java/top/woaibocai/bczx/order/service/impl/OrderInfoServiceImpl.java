package top.woaibocai.bczx.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.feign.CartFeignClient;
import top.woaibocai.bczx.feign.product.ProductFeignClient;
import top.woaibocai.bczx.feign.user.UserFeignClient;
import top.woaibocai.bczx.model.dto.h5.OrderInfoDto;
import top.woaibocai.bczx.model.entity.h5.CartInfo;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.entity.order.OrderItem;
import top.woaibocai.bczx.model.entity.order.OrderLog;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.model.entity.user.UserAddress;
import top.woaibocai.bczx.model.entity.user.UserInfo;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.h5.TradeVo;
import top.woaibocai.bczx.order.mapper.OrderInfoMapper;
import top.woaibocai.bczx.order.mapper.OrderItemMapper;
import top.woaibocai.bczx.order.mapper.OrderLogMapper;
import top.woaibocai.bczx.order.service.OrderInfoService;
import top.woaibocai.bczx.utils.AuthContextUtil;

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
    @Resource
    private ProductFeignClient productFeignClient;
    @Resource
    private UserFeignClient userFeignClient;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private OrderLogMapper orderLogMapper;
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

    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        //1.orderInfoDto 获取所有订单想list list<OrderItem>
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        //2.判断list<OrderItem> 为空，抛出异常
        if (CollectionUtils.isEmpty(orderItemList)){
            throw new BoCaiException(ResultCodeEnum.DATA_ERROR);
        }
        //3.校验商品库存是否充足
        //遍历list<OrederItem>集合，得到每个orderItem
        orderItemList.forEach(orderItem -> {
            //校验每个orderItem库存量是否充足
            //远程调用获取商品sku信息，包含库存量
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if (productSku == null){
                throw new BoCaiException(ResultCodeEnum.DATA_ERROR);
            }
            if (productSku.getStockNum() < orderItem.getSkuNum()){
                throw new BoCaiException(ResultCodeEnum.STOCK_LESS);
            }
        });

        //4.添加数据到orderInfo表
        //封装数据到orderinfo对象里
        OrderInfo orderInfo = new OrderInfo();
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        orderInfo.setUserId(userInfo.getId());
        orderInfo.setNickName(userInfo.getNickName());
        //封装收货信息
        Long userAddressId = orderInfoDto.getUserAddressId();
        //用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        //远程调用：获取用户收货地址信息


        orderInfoMapper.insert(orderInfo);

        //5.添加数据到orderItem表
        //添加List<OrderItem>里面数据，把集合每个orderItem添加表
        for (OrderItem orderItem:orderItemList){
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        }
        //6.添加数据到orderLog表
        //封装数据到对象里
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.insert(orderLog);
        //7.把生成订单商品，从购物车里清除 TODO 远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();
        //8.返回订单id
        return orderInfo.getId();
    }

    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.selectOrederById(orderId);
    }

    @Override
    public TradeVo buy(Long skuId) {
        //封装集合
        List<OrderItem> orderItemList = new ArrayList<>();
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(productSku.getSalePrice());
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
    }

    @Override
    public PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page,limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<OrderInfo> orderInfoList = orderInfoMapper.findOrderPage(userId,orderStatus);

        //查询订单里的所所有订单项
        orderInfoList.forEach(orderInfo -> {
            //订单id查询订单里的订单项
            List<OrderItem> orderItemList = orderInfoMapper.findOrderId(orderInfo.getId());
            //封装
            orderInfo.setOrderItemList(orderItemList);
        });
        return new PageInfo<>(orderInfoList);
    }

    @Override
    public OrderInfo getOrderInfoByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfoByOrderNo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }
}
