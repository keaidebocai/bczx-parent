package top.woaibocai.bczx.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.entity.product.Brand;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.product.service.BrandService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-30 18:01
 **/
@Tag(name = "品牌")
@RestController
@RequestMapping("api/product/brand")
public class BrandController {
    @Resource
    private BrandService brandService;
    @Operation(summary = "获取全部品牌")
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
