package top.woaibocai.bczx.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.model.entity.user.UserAddress;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.user.service.UserAddressService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-02 16:18
 **/
@Tag(name = "用户接口地址")
@RestController
@RequestMapping("/api/user/userAddress")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;

    @Operation(summary = "获取用户地址列表")
    @GetMapping("auth/findUserAddressList")
    public Result findUserAddressList(){
        List<UserAddress> userAddressList = userAddressService.findUserAddressList();
        return Result.build(userAddressList, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "根据id获取收货地址信息")
    @GetMapping("getUserAddress/{id}")
    public UserAddress getUserAddress(@PathVariable Long id){
        return userAddressService.getUserAddress(id);
    }
}
