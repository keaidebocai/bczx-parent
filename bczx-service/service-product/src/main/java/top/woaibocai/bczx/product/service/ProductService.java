package top.woaibocai.bczx.product.service;

import top.woaibocai.bczx.model.entity.product.ProductSku;

import java.util.List;

public interface ProductService {
    List<ProductSku> selectProductSkuBySale();
}
