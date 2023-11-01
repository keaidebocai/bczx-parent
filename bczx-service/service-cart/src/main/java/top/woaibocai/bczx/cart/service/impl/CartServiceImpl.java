package top.woaibocai.bczx.cart.service.impl;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.cart.service.CartService;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.feign.product.ProductFeignClient;
import top.woaibocai.bczx.model.entity.h5.CartInfo;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.model.entity.user.UserInfo;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.utils.AuthContextUtil;

import java.util.Date;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-01 15:43
 **/
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private ProductFeignClient productFeignClient;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    private String getCartKey(Long userId){
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //1.必须登录
        //从ThreadLocal获取用户信息
        Long userId = AuthContextUtil.getUserInfo().getId();
        //构建hash类型key名称
        String cartKey = getCartKey(userId);

        //2.因为从购物车放到redis
        //hash类型，key:userId field:skuId value:sku信息CartInfo
        //从redis里面获取购物车数据，根据用户id+skuId获取(hash类型key+field)
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, String.valueOf(skuId));

        //3.如果购物车存在购物车商品，把商品数量相加
        CartInfo cartInfo = null;
        if (cartInfoObj != null){//添加购物车商品已经存在，把商品数量相加
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            //数量相加
            cartInfo.setSkuNum(cartInfo.getSkuNum() + skuNum);
            cartInfo.setIsChecked(1);
            cartInfo.setUpdateTime(new Date());
        }else {
            //4.如果购物车没有添加商品，直接商品添加购物车(添加到redis里面)
            //根据skuId获取商品sku信息
            cartInfo = new CartInfo();

            //TODO:远程调用实现：根据skuId获取商品信息
            ProductSku productSku = productFeignClient.getBySkuId(skuId);
            // 购物车数据是从商品详情得到 {skuInfo}
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }
        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey , String.valueOf(skuId) , JSON.toJSONString(cartInfo));
    }
}
