package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.dto.product.ProductDto;
import top.woaibocai.bczx.model.entity.product.Product;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.ProductService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-25 16:12
 **/
@Tag(name = "商品管理")
@RestController
@RequestMapping("admin/product/product")
public class ProductController {
    @Resource
    private ProductService productService;
    @Operation(summary = "条件分页查询")
    @GetMapping("/{page}/{limit}")
    public Result list(@PathVariable Integer page,
                       @PathVariable Integer limit,
                       ProductDto productDto){
        PageInfo<Product> pageInfo = productService.findByPage(page,limit,productDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}
