package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.system.SysUserDto;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.SysUserService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-21 12:26
 **/
@Tag(name = "用户管理接口")
@RestController
@RequestMapping("admin/system/sysUser")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;
    //用户条件分页查询接口
    @Operation(summary = "用户条件分页查询接口")
    @PostMapping("findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Long pageNum,
                             @PathVariable("pageSize") Long pageSize,
                             @RequestBody SysUserDto sysUserDto){
        IPage<SysUser> iPage = sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(iPage, ResultCodeEnum.SUCCESS);
    }
    //2.用户添加
    @Operation(summary = "添加用户接口")
    @PostMapping("saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //3.用户修改
    @Operation(summary = "修改用户接口")
    @PutMapping("updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    //4.用户删除
    @Operation(summary = "删除用户接口")
    @DeleteMapping("deleteById/{userId}")
    public Result deleteById(@PathVariable("userId") Long userId){
        sysUserService.deleteById(userId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}