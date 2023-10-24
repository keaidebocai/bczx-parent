package top.woaibocai.bczx.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.CategoryService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-24 10:45
 **/
@Tag(name = "分类管理")
@RestController
@RequestMapping("admin/product/category")
public class CategoryController {
    @Resource
    private CategoryService categoryService;
    @Operation(summary = "导入")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        //获取上传文件
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "导出")
    @GetMapping("exportData")
    public void exportData (HttpServletResponse response){
        categoryService.exportData(response);
    }
    @Operation(summary = "分类列表，每次查询一层数据")
    @GetMapping("findCategoryList/{id}")
    public Result findCategoryList(@PathVariable Long id){
        List<Category> list = categoryService.findCategoryList(id);
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

}
