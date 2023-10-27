package top.woaibocai.bczx.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.product.ProductSku;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> selectProductSkuBySale();
}
