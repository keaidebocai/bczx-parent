package top.woaibocai.bczx.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import top.woaibocai.bczx.model.dto.h5.ProductSkuDto;
import top.woaibocai.bczx.model.dto.product.ProductDto;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.model.vo.h5.ProductItemVo;

import java.util.List;

public interface ProductService {
    List<ProductSku> selectProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);
}
