package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.woaibocai.bczx.model.entity.system.SysMenu;
import top.woaibocai.bczx.model.other.SysMenuTree;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenuTree> findMenusByUserId(Long id);

    SysMenu seleParentMenu(Long parentId);
}
