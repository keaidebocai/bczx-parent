package top.woaibocai.bczx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.model.dto.h5.UserLoginDto;
import top.woaibocai.bczx.model.dto.h5.UserRegisterDto;
import top.woaibocai.bczx.model.entity.user.UserInfo;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.h5.UserInfoVo;
import top.woaibocai.bczx.user.mapper.UserInfoMapper;
import top.woaibocai.bczx.user.service.UserInfoService;
import top.woaibocai.bczx.user.utils.BeanCopyUtils;
import top.woaibocai.bczx.utils.AuthContextUtil;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //1.userRegisterDto获取数据
        String code = userRegisterDto.getCode();
        String password = userRegisterDto.getPassword();
        //用户名就是手机号
        String username = userRegisterDto.getUsername();
        String nickName = userRegisterDto.getNickName();
        //2.验证码校验
        String redisCode = redisTemplate.opsForValue().get(username);
        //2.1 获取输入的验证码，进行对比
        if (!Objects.equals(redisCode, code)){
            throw new BoCaiException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //3. 校验用户名不能重复
        LambdaQueryWrapper<UserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserInfo::getUsername,username);
        Long count = userInfoMapper.selectCount(wrapper);
        if (count > 0){
            throw new BoCaiException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //4.封装添加数据，调用方法添加数据库
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setNickName(nickName);
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("https://qiniu.woaibocai.top/static/img/tou.png");
        userInfoMapper.insert(userInfo);
        //5.从redis删除发送的验证码
        redisTemplate.delete(userInfo.getPhone());
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        //1.dto获取用户名和密码
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        //2.根据用户名查询数据库，得到用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserName(username);
        if (userInfo == null){
            throw new BoCaiException(ResultCodeEnum.LOGIN_ERROR);
        }
        //3.比较密码是否一致
        if (!password.equals(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes()))){
            throw new BoCaiException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4.校验用户是否禁用
        if (userInfo.getStatus() == 0){
            throw new BoCaiException(ResultCodeEnum.ACCOUNT_STOP);
        }
        //5.生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //6.把用户的信息放到redis里
        redisTemplate.opsForValue().set("user:bczx:"+token,JSON.toJSONString(userInfo),7, TimeUnit.DAYS);
        //7.返回token
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //从redis根据token获取信息
//        String userJson = redisTemplate.opsForValue().get("user:bczx:" + token);
//        if (!StringUtils.hasText(userJson)){
//            throw new BoCaiException(ResultCodeEnum.LOGIN_AUTH);
//        }
//        UserInfo userInfo = JSON.parseObject(userJson, UserInfo.class);

        //从ThreadLocal获取信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(userInfo, UserInfoVo.class);
        return userInfoVo;
    }
}
