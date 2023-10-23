package top.woaibocai.bczx.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.dto.system.AssginMenuDto;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper{

    List<Long> findSysRoleMenuByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
