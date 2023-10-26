package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.product.CategoryBrandDto;
import top.woaibocai.bczx.model.entity.product.Brand;
import top.woaibocai.bczx.model.entity.product.CategoryBrand;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.CategoryBrandService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-24 17:26
 **/
@RestController
@RequestMapping("admin/product/categoryBrand")
public class CategoryBrandController {
    @Resource
    private CategoryBrandService categoryBrandService;

    @Operation(summary = "分类id查询对应的品牌数据")
    @GetMapping("findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId){
        List<Brand> list = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加")
    @PostMapping("save")
    public Result save(@RequestBody CategoryBrand categoryBrand){
        categoryBrandService.save(categoryBrand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "分类品牌分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findBypage(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page,limit,categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
