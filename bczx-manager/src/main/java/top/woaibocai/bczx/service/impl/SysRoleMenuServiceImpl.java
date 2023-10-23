package top.woaibocai.bczx.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.SysMenuMapper;
import top.woaibocai.bczx.mapper.SysRoleMenuMapper;
import top.woaibocai.bczx.model.dto.system.AssginMenuDto;
import top.woaibocai.bczx.model.other.SysMenuTree;
import top.woaibocai.bczx.service.SysMenuService;
import top.woaibocai.bczx.service.SysRoleMenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-23 15:25
 **/
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysMenuService sysMenuService;
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        //查询所有菜单
        List<SysMenuTree> nodes = sysMenuService.findNodes();

        //查询角色分配过的列表
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        Map<String,Object> map = new HashMap<>();
        map.put("sysMenuList",nodes);
        map.put("roleMenuIds",roleMenuIds);
        return map;
    }
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        //删除角色分配到的id
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        //保存分配数据
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if (menuInfo!= null){
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }
    }


}
