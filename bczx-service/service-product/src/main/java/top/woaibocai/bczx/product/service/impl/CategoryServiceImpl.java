package top.woaibocai.bczx.product.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.product.mapper.CategoryMapper;
import top.woaibocai.bczx.product.service.CategoryService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-27 16:52
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> selectOneCategory() {
        List<Category> categoryList = categoryMapper.selectOneCategory();
        return categoryList;
    }
}
