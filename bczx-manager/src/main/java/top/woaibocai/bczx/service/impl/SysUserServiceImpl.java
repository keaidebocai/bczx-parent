package top.woaibocai.bczx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.manager.SysUserMapper;
import top.woaibocai.bczx.model.dto.system.LoginDto;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.system.LoginVo;
import top.woaibocai.bczx.service.SysUserService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-19 14:49
 **/
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public LoginVo login(LoginDto loginDto) {
        //先从redis里获取相关kv
        String redisCaptcha = redisTemplate.opsForValue().get("user:validate" + loginDto.getCodeKey());
        if (!StrUtil.equalsIgnoreCase(redisCaptcha,loginDto.getCaptcha()) || StrUtil.isEmpty(redisCaptcha)){
            throw new BoCaiException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //如果一致，那就删除redis里的验证码
        Boolean delete = redisTemplate.delete("user:validate" + loginDto.getCodeKey());
        if (delete){
            System.out.println("=============================================================");
        }

        //1.获取提交用户名，loginDto获取到
        String dtoUserName = loginDto.getUserName();
        //2.根据用户查询数据表 sys_user表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(dtoUserName);
        //3.如果根据用户名查不到对应信息，用户不存在，返回错误信息
        if (sysUser == null){
            throw new BoCaiException(ResultCodeEnum.LOGIN_ERROR);
        }
        //4.如果根据用户名查询到用户信息，用户存在
        //5.获取输入的密码，比较输入的密码和数据库中密码是否一致
        String loginDtoPassword = loginDto.getPassword();
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(loginDtoPassword.getBytes());
        //6.如果密码一直，登录成功，如果密码不一致登陆失败
       if (!md5DigestAsHex.equals(sysUser.getPassword())){
           throw new BoCaiException(ResultCodeEnum.LOGIN_ERROR);
       }
        //7.登陆成功，生成用户唯一表示token
        String token = UUID.randomUUID().toString().replace("-", "");
        //8.把登录成功用户信息放到redis里面
        redisTemplate.opsForValue().set("user:login" + token ,
                JSON.toJSONString(sysUser) , 7, TimeUnit.DAYS);
        //9.返回loginVo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        loginVo.setRefresh_token("");
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }
}
