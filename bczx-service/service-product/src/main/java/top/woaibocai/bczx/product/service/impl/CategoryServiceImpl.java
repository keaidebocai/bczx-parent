package top.woaibocai.bczx.product.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.product.mapper.CategoryMapper;
import top.woaibocai.bczx.product.service.CategoryService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    @Override
    public List<Category> findCategoryTree() {
        //先查询所有的数据
        List<Category> categoryList = categoryMapper.selectList();
        //得到一级
//        List<Category> oneCategoryList = categoryList.stream()
//                .filter(category -> category.getParentId().longValue() == 0)
//                .collect(Collectors.toList());
//
//        oneCategoryList.forEach(oneCategory -> {
//            List<Category> twoCategoryList = categoryList.stream()
//                    .filter(item -> item.getParentId() == oneCategory.getId())
//                    .collect(Collectors.toList());
//            //封装
//            oneCategory.setChildren(twoCategoryList);
//
//
//            twoCategoryList.forEach(twoCategory ->{
//                List<Category> threeCategoryList = categoryList.stream()
//                        .filter(item -> item.getParentId() == twoCategory.getId())
//                        .collect(Collectors.toList());
//                twoCategory.setChildren(threeCategoryList);
//            });
//        });
//        return oneCategoryList;
        /////////////////////for
        //获得一级分类
        List<Category> oneCategory = new ArrayList<>();
        for (int i = 0; i < categoryList.size(); i++) {
            Category category = categoryList.get(i);
            if (category.getParentId() == 0){
                oneCategory.add(category);
            }
        }
        for (int i = 0; i < oneCategory.size(); i++) {
            Category categoryOne = oneCategory.get(i);
            List<Category> twoCategoryList = new ArrayList<>();
            for (int j = 0; j <categoryList.size(); j++) {
                Category categoryTwo = categoryList.get(j);
                if (categoryOne.getId() == categoryTwo.getParentId()){
                    twoCategoryList.add(categoryTwo);
                }
            }
            for (int j = 0; j < twoCategoryList.size(); j++) {
                Category categoryTwo = twoCategoryList.get(j);
                List<Category> threeCategoryList = new ArrayList<>();
                for (int k = 0; k < categoryList.size(); k++) {
                    Category categoryThree = categoryList.get(k);
                    if (categoryThree.getParentId() == categoryTwo.getId()){
                        threeCategoryList.add(categoryThree);
                    }
                }
                categoryTwo.setChildren(threeCategoryList);
            }
            categoryOne.setChildren(twoCategoryList);
        }
        return oneCategory;
    }
}
