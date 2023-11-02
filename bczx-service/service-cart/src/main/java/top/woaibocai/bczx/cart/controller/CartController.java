package top.woaibocai.bczx.cart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.cart.service.CartService;
import top.woaibocai.bczx.model.entity.h5.CartInfo;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-01 15:42
 **/
@Tag(name = "购物车")
@RestController
@RequestMapping("api/order/cart")
public class CartController {
    @Resource
    private CartService cartService;
    @Operation(summary = "清空购物车")
    @GetMapping("auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "更新购物车商品全部选中状态")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@PathVariable Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "更新购物车选中状态")
    @GetMapping("auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId,
                            @PathVariable Integer isChecked){
        cartService.checkCart(skuId,isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "删除购物车")
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId){
        cartService.deleteCart(skuId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "查询购物车")
    @GetMapping("auth/cartList")
    public Result cartList(){
        List<CartInfo> cartInfoList = cartService.getcartList();
        return Result.build(cartInfoList,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "添加购物车")
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId,
                            @PathVariable Integer skuNum){
        cartService.addToCart(skuId,skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
