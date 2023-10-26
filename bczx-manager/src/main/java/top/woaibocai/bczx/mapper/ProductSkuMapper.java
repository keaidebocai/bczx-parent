package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.product.ProductSku;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-26 12:54
 **/
@Mapper
public interface ProductSkuMapper extends BaseMapper<ProductSku> {
    void save(ProductSku productSku);

    void updateByProductId(Long id);
}
