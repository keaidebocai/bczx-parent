package top.woaibocai.bczx.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.h5.UserLoginDto;
import top.woaibocai.bczx.model.dto.h5.UserRegisterDto;
import top.woaibocai.bczx.model.entity.user.UserInfo;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.h5.UserInfoVo;
import top.woaibocai.bczx.user.service.UserInfoService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-31 18:47
 **/
@Tag(name = "用户接口")
@RestController
@RequestMapping("api/user/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;
    @Operation(summary = "获取当前用户信息")
    @GetMapping("auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        UserInfoVo userInfoVo = userInfoService.getCurrentUserInfo(token);
        return Result.build(userInfoVo,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "会员登陆")
    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto){
        String token = userInfoService.login(userLoginDto);
        return Result.build(token,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "保存注册信息")
    @PostMapping("register")
    public Result register(@RequestBody UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
