package top.woaibocai.bczx.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.h5.IndexVo;
import top.woaibocai.bczx.product.service.CategoryService;
import top.woaibocai.bczx.product.service.ProductService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description: 首页管理接口
 * @author: woaibocai
 * @create: 2023-10-27 16:50
 **/
@Tag(name = "首页管理接口")
@RestController
@RequestMapping("/api/product/index")
@CrossOrigin
public class IndexController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;
    @Operation(summary = "查询首页")
    @GetMapping
    public Result index(){
        //1.所有一级分类
        List<Category> categoryList = categoryService.selectOneCategory();
        //2.根据销量排序，获取前10条记录
        List<ProductSku> productSkuList = productService.selectProductSkuBySale();

        //3.封装对象到vo里
        IndexVo indexVo = new IndexVo();
        indexVo.setCategoryList(categoryList);
        indexVo.setProductSkuList(productSkuList);
        return Result.build(indexVo, ResultCodeEnum.SUCCESS);
    }
}
