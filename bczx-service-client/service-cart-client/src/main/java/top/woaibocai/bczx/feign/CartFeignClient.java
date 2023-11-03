package top.woaibocai.bczx.feign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.woaibocai.bczx.model.entity.h5.CartInfo;
import top.woaibocai.bczx.model.vo.common.Result;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-02 17:05
 **/
@Tag(name = "cart远程调用")
@FeignClient(value = "service-cart")
public interface CartFeignClient {
    @Operation(summary = "用于远程调用，获取购物车中选中的商品")
    @GetMapping("/api/order/cart/auth/getAllCkecked")
    public List<CartInfo> getAllCkecked();

    @Operation(summary = "删除生成订单购物车的商品")
    @GetMapping("/api/order/cart/auth/deleteChecked")
    public Result deleteChecked();
}
