package top.woaibocai.bczx.service;

import top.woaibocai.bczx.model.dto.system.LoginDto;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.system.LoginVo;

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);
}
