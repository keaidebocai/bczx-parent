package top.woaibocai.bczx.user.service;

import top.woaibocai.bczx.model.dto.h5.UserLoginDto;
import top.woaibocai.bczx.model.dto.h5.UserRegisterDto;
import top.woaibocai.bczx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}
