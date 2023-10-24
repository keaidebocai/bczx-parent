package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.dto.product.CategoryBrandDto;
import top.woaibocai.bczx.model.entity.product.CategoryBrand;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.CategoryBrandService;

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

    @Operation(summary = "分类品牌分页查询")
    @GetMapping("/{page}/{limit}")
    public Result findBypage(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page,limit,categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
