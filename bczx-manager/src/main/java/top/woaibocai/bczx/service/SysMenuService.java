package top.woaibocai.bczx.service;

import top.woaibocai.bczx.model.entity.system.SysMenu;
import top.woaibocai.bczx.model.other.SysMenuTree;

import java.util.List;

public interface SysMenuService {
    List<SysMenuTree> findNodes();

    void save(SysMenu sysMenu);

    void update(SysMenu sysMenu);

    void removeById(Long id);
}
