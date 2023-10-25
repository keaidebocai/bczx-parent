package top.woaibocai.bczx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.woaibocai.bczx.model.entity.product.ProductSpec;

public interface ProductSpecService {
    IPage<ProductSpec> findByPage(Integer page, Integer limit);

    void save(ProductSpec productSpec);

    void updateById(ProductSpec productSpec);

    void deleteById(Long id);
}
