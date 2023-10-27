package top.woaibocai.bczx.product.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.product.mapper.ProductSkuMapper;
import top.woaibocai.bczx.product.service.ProductService;

import java.util.List;

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
    @Override
    public List<ProductSku> selectProductSkuBySale() {
        List<ProductSku> productSkuList = productSkuMapper.selectProductSkuBySale();
        return productSkuList;
    }
}
