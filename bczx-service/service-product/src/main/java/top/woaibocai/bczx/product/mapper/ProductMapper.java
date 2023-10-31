package top.woaibocai.bczx.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.product.Product;
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    Product selectByThisId(Long productId);
}
