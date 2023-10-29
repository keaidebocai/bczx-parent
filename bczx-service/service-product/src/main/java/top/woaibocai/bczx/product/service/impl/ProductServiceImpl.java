package top.woaibocai.bczx.product.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.entity.product.Product;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.product.mapper.ProductSkuMapper;
import top.woaibocai.bczx.product.service.ProductService;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-27 16:53
 **/
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public List<ProductSku> selectProductSkuBySale() {
        String hostProductSku = redisTemplate.opsForValue().get("bczx:hostProduct");
        if (!StringUtils.isEmpty(hostProductSku)){
            List<ProductSku> hostProductList = JSON.parseArray(hostProductSku, ProductSku.class);
            return hostProductList;
        }
        List<ProductSku> productSkuList = productSkuMapper.selectProductSkuBySale();
        String toJSONString = JSON.toJSONString(productSkuList);
        redisTemplate.opsForValue().set("bczx:hostProduct",toJSONString,7, TimeUnit.DAYS);
        return productSkuList;
    }
}
