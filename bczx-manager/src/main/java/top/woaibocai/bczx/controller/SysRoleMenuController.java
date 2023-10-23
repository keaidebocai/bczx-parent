package top.woaibocai.bczx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.system.AssginMenuDto;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.SysRoleMenuService;

import java.util.List;
import java.util.Map;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-23 15:23
 **/
@Tag(name = "分配菜单")
@RestController
@RequestMapping("admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Resource
    private SysRoleMenuService sysRoleMenuService;
    @Operation(summary = "查询所有菜单和查询角色分配过的菜单id列表")
    @GetMapping("findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId){
        Map<String,Object> map = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(map, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "保存角色分配菜单数据")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);


    }

}
