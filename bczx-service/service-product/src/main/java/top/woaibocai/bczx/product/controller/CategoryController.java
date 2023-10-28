package top.woaibocai.bczx.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.product.service.CategoryService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-28 12:30
 **/
@RestController
@RequestMapping("api/product/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Operation(summary = "查询所有分类，树形封装")
    @GetMapping("findCategoryTree")
    public Result findCategoryTree(){
        List<Category> list = categoryService.findCategoryTree();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
