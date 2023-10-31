package top.woaibocai.bczx.user.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.dto.h5.UserRegisterDto;
import top.woaibocai.bczx.user.UserInfoMapper;
import top.woaibocai.bczx.user.service.UserInfoService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-31 18:48
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //1.userRegisterDto获取数据

        //2.验证码校验

        //2.1 获取输入的验证码，进行对比

        //3. 校验用户名不能重复

        //4.封装添加数据，调用方法添加数据库

        //5.从redis删除发送的验证码
    }
}
