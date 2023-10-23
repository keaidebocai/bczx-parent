package top.woaibocai.bczx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.SysRoleMapper;
import top.woaibocai.bczx.mapper.SysUserRoleMapper;
import top.woaibocai.bczx.model.dto.system.SysRoleDto;
import top.woaibocai.bczx.model.entity.system.SysRole;
import top.woaibocai.bczx.model.entity.system.SysUserRole;
import top.woaibocai.bczx.service.SysRoleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-20 14:21
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public IPage<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //1.根据条件json把单条数据查出来
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(sysRoleDto.getRoleName() != null,SysRole::getRoleName,sysRoleDto.getRoleName())
                .orderByDesc(SysRole::getCreateTime);
        //2.分页
        IPage<SysRole> iPage = new Page<>();
        iPage.setCurrent(current);
        iPage.setSize(limit);
        IPage<SysRole> iPage1 = sysRoleMapper.selectPage(iPage, wrapper);
        return iPage1;
    }

    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public Map<String, Object> findAll(Long userId) {
        //查询所有角色
        List<SysRole> roleList = sysRoleMapper.selectList(null);
        //2.分配过的角色列表
        //根据userId查询分配过的角色信息
        List<Long> userRoles = sysUserRoleMapper.selectRoleIdsByUserId(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("allRolesList",roleList);
        map.put("sysUserRoles",userRoles);
        return map;
    }
}
