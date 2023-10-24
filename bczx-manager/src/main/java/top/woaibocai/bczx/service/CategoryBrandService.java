package top.woaibocai.bczx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import top.woaibocai.bczx.model.dto.product.CategoryBrandDto;
import top.woaibocai.bczx.model.entity.product.CategoryBrand;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);
}
