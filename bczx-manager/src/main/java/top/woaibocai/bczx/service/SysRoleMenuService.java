package top.woaibocai.bczx.service;

import top.woaibocai.bczx.model.dto.system.AssginMenuDto;

import java.util.List;
import java.util.Map;

public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);
    void doAssign(AssginMenuDto assginMenuDto);
}
