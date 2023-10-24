package top.woaibocai.bczx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.woaibocai.bczx.model.entity.product.Brand;

public interface BrandService {
    IPage<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);
}
