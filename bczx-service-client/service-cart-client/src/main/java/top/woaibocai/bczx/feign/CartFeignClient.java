package top.woaibocai.bczx.feign;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import top.woaibocai.bczx.model.entity.h5.CartInfo;

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
}
