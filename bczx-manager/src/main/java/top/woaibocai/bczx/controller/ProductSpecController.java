package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.entity.product.ProductSpec;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.ProductSpecService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-25 10:18
 **/
@Tag(name = "品牌规格管理")
@RestController
@RequestMapping("admin/product/productSpec")
public class ProductSpecController {
    @Resource
    private ProductSpecService productSpecService;
    @Operation(summary = "查询所有")
    @GetMapping("findAll")
    public Result findAll(){
        List<ProductSpec> list = productSpecService.findAll();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "列表")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit){
        IPage<ProductSpec> iPage = productSpecService.findByPage(page,limit);
        return Result.build(iPage, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "添加")
    @PostMapping("save")
    public Result save(@RequestBody ProductSpec productSpec){
        productSpecService.save(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "修改")
    @PutMapping("updateById")
    public Result updateById(@RequestBody ProductSpec productSpec){
        productSpecService.updateById(productSpec);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "删除")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        productSpecService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
