package top.woaibocai.bczx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.woaibocai.bczx.model.dto.system.LoginDto;
import top.woaibocai.bczx.model.dto.system.SysUserDto;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    IPage<SysUser> findByPage(Long pageNum, Long pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);
}
