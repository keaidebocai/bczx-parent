package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.model.vo.product.CategoryExcelVo;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> selectByParentId(Long id);

    int countByParentId(Long id);

    List<Category> findAll();

    void batchInsert(List<CategoryExcelVo> categoryList);
}
