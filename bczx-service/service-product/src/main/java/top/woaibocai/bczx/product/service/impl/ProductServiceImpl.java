package top.woaibocai.bczx.product.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.dto.h5.ProductSkuDto;
import top.woaibocai.bczx.model.dto.product.ProductDto;
import top.woaibocai.bczx.model.entity.product.Product;
import top.woaibocai.bczx.model.entity.product.ProductDetails;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.model.vo.h5.ProductItemVo;
import top.woaibocai.bczx.product.mapper.ProductDetailsMapper;
import top.woaibocai.bczx.product.mapper.ProductMapper;
import top.woaibocai.bczx.product.mapper.ProductSkuMapper;
import top.woaibocai.bczx.product.service.ProductService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private ProductMapper productMapper;
    @Resource
    private ProductDetailsMapper productDetailsMapper;
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

    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> list = productSkuMapper.findByPage(productSkuDto);
        return new PageInfo<>(list);
    }

    @Override
    public ProductItemVo item(Long skuId) {
        //创建一个vo对象，用于封装最终数据
        ProductItemVo productItemVo = new ProductItemVo();
        //获取sku获取skuId
        ProductSku productSku = productSkuMapper.selectById(skuId);
        //根据sku获取produvtID，
        Product product = productMapper.selectByThisId(productSku.getProductId());
        //获取product规格信息
        ProductDetails productDetails = productDetailsMapper.selectOne(new LambdaQueryWrapper<ProductDetails>().eq(ProductDetails::getProductId, productSku.getProductId()));
        //获取所有的sku
        List<ProductSku> productSkuList = productSkuMapper.selectList(new LambdaQueryWrapper<ProductSku>().eq(ProductSku::getProductId, productSku.getProductId()));
        //封装map集合  == 商品规格对应商品skuId信息
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item ->{
            skuSpecValueMap.put(item.getSkuSpec(),item.getId());
        });
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);
        //封装详情组件
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        return productItemVo;
    }
}
