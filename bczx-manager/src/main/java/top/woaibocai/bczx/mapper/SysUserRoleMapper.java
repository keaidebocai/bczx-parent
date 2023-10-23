package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.woaibocai.bczx.model.entity.system.SysUserRole;

import java.util.List;

@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    void deleteByUserId(Long userId);

    /**
    * @Description: 批量添加用户权限
    * @Param: [userId, roleIdList]
    * @return: void
    * @Author: woaibocai
    * @Date: 2023/10/22
    */
    void bathInsetRole(@Param("userId") Long userId, @Param("roleIdList") List<Long> roleIdList);

    List<Long> selectRoleIdsByUserId(Long userId);
}
