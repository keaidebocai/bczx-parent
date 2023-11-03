package top.woaibocai.bczx.feign.product;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.entity.product.ProductSku;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-01 17:30
 **/
@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @Operation(summary = "远程调用：根据skuId返回sku信息")
    @GetMapping("api/product/getBySkuId/{skuId}")
    public ProductSku getBySkuId(@PathVariable("skuId") Long skuId);
}
