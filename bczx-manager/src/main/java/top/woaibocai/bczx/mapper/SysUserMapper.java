package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.system.SysUser;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    SysUser selectUserInfoByUserName(String dtoUserName);
}
