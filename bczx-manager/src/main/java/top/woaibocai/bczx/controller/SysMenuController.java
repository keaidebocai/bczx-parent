package top.woaibocai.bczx.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.entity.system.SysMenu;
import top.woaibocai.bczx.model.other.SysMenuTree;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.SysMenuService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-23 11:06
 **/
@Tag(name = "菜单管理接口")
@RestController
@RequestMapping("admin/system/sysMenu")
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;
    @Operation(summary = "菜单删除")
    @DeleteMapping("remove/{id}")
    public Result removeById(@PathVariable("id") Long id){
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "菜单修改")
    @PutMapping("update")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "菜单添加")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "菜单列表")
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysMenuTree> list = sysMenuService.findNodes();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }
}
