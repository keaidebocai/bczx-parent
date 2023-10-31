package top.woaibocai.bczx.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.dto.h5.ProductSkuDto;
import top.woaibocai.bczx.model.dto.product.ProductDto;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.h5.ProductItemVo;
import top.woaibocai.bczx.product.service.ProductService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-30 18:10
 **/
@Tag(name = "商品接口")
@RestController
@RequestMapping("api/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result item(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "分页查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       ProductSkuDto productSkuDto){
        PageInfo<ProductSku> pageInfo  = productService.findByPage(page,limit,productSkuDto);

        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
