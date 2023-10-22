package top.woaibocai.bczx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.system.LoginDto;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.system.LoginVo;
import top.woaibocai.bczx.model.vo.system.ValidateCodeVo;
import top.woaibocai.bczx.service.SysUserService;
import top.woaibocai.bczx.service.ValidateCodeService;
import top.woaibocai.bczx.utils.AuthContextUtil;

/**
 * @program: bczx-parent
 * @description: 用户接口
 * @author: woaibocai
 * @create: 2023-10-19 14:45
 **/
@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Resource
    private SysUserService sysUserService ;
    @Resource
    private ValidateCodeService validateCodeService;

    @Operation(summary = "登录接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto) {
        LoginVo loginVo = sysUserService.login(loginDto) ;
        return Result.build(loginVo , ResultCodeEnum.SUCCESS) ;
    }
    @Operation(summary = "图形验证码")
    @GetMapping(value = "/generateValidateCode")
    public Result generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        System.out.println(validateCodeVo);
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }

//    @Operation(summary = "获取当前用户信息")
//    @GetMapping(value = "/getUserInfo")
//    public Result getUserInfo(@RequestHeader(name = "token") String token){
//        //1.从请求头里获取token
//        //2.根据token，查询redis获取的用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token);
//        //3。用户返回
//        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
//    }
    @Operation(summary = "获取当前用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(){
        return Result.build(AuthContextUtil.get(),ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "退出登录")
    @GetMapping("logout")
    public Result logout(@RequestHeader(name ="token") String token){
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
