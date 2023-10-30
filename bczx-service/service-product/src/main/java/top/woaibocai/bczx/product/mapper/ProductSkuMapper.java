package top.woaibocai.bczx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.dto.h5.ProductSkuDto;
import top.woaibocai.bczx.model.entity.product.ProductSku;

import java.util.List;

@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
    List<ProductSku> selectProductSkuBySale();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);
}
