package top.woaibocai.bczx.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.product.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> selectOneCategory();

}
