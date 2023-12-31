package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.common.log.annotation.Log;
import top.woaibocai.bczx.common.log.enums.OperatorType;
import top.woaibocai.bczx.model.entity.product.Brand;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.BrandService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-24 16:05
 **/
@Tag(name = "品牌管理接口")
@RestController
@RequestMapping("admin/product/brand")
public class BrandController {
    @Resource
    private BrandService brandService;
    @Operation(summary = "查询所有")
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> list = brandService.findAll();
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
    @Log(title = "品牌管理：列表",businessType = 0,operatorType = OperatorType.OTHER)
    @Operation(summary = "列表分页")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit){
        IPage<Brand> pageInfo = brandService.findByPage(page,limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加品牌")
    @PostMapping("save")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
