package top.woaibocai.bczx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.system.SysRoleDto;
import top.woaibocai.bczx.model.entity.system.SysRole;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.SysRoleService;

import java.util.Map;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-20 14:14
 **/
@Tag(name = "角色接口")
@RestController
@RequestMapping("admin/system/sysRole")
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;
    //current：当前页 limit：每页显示记录数
    //SysRoleDto 条件角色名称对象
    @Operation(summary = "分页接口")
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto){
        IPage<SysRole> iPage = sysRoleService.findByPage(sysRoleDto,current,limit);
        return Result.build(iPage, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "角色添加")
    @PostMapping("saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色修改")
    @PutMapping("updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "角色删除")
    @DeleteMapping("deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId ){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "查询所有角色")
    @GetMapping("findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable("userId") Long userId){
        Map<String,Object> map = sysRoleService.findAll(userId);
        return Result.build(map,ResultCodeEnum.SUCCESS);
    }

}
