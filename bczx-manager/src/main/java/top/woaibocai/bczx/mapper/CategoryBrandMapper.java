package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.dto.product.CategoryBrandDto;
import top.woaibocai.bczx.model.entity.product.CategoryBrand;

import java.util.List;

@Mapper
public interface CategoryBrandMapper extends BaseMapper<CategoryBrand> {
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);
}
