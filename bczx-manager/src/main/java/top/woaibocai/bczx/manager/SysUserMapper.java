package top.woaibocai.bczx.manager;

import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.system.SysUser;

@Mapper
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String dtoUserName);
}
