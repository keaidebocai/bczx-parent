package top.woaibocai.bczx.feign.user;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import top.woaibocai.bczx.model.entity.user.UserAddress;

@FeignClient("service-user")
public interface UserFeignClient {
    @Operation(summary = "根据id获取收货地址信息")
    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id);
}
