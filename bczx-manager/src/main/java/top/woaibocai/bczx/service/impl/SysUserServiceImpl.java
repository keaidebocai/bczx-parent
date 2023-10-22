package top.woaibocai.bczx.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.mapper.SysUserRoleMapper;
import top.woaibocai.bczx.mapper.SysUserMapper;
import top.woaibocai.bczx.model.dto.system.AssginRoleDto;
import top.woaibocai.bczx.model.dto.system.LoginDto;
import top.woaibocai.bczx.model.dto.system.SysUserDto;
import top.woaibocai.bczx.model.entity.system.SysUserRole;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.system.LoginVo;
import top.woaibocai.bczx.service.SysUserService;

import java.util.List;
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
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;
    @Override
    public LoginVo login(LoginDto loginDto) {
        //先从redis里获取相关kv
        String redisCaptcha = redisTemplate.opsForValue().get("user:validate" + loginDto.getCodeKey());
        if (!StrUtil.equalsIgnoreCase(redisCaptcha,loginDto.getCaptcha()) || StrUtil.isEmpty(redisCaptcha)){
            throw new BoCaiException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //如果一致，那就删除redis里的验证码
        redisTemplate.delete("user:validate" + loginDto.getCodeKey());
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

    @Override
    public IPage<SysUser> findByPage(Long pageNum, Long pageSize, SysUserDto sysUserDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .orderByDesc(SysUser::getCreateTime)
                .like(sysUserDto.getKeyword() != null,SysUser::getUsername,sysUserDto.getKeyword());
        //分页
        IPage<SysUser> page= new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        IPage<SysUser> iPage = sysUserMapper.selectPage(page, queryWrapper);
        return iPage;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        //username不可重复
        SysUser sysUser1 = sysUserMapper.selectById(sysUser.getUsername());
        if (sysUser1!=null){
            throw new BoCaiException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //给密码加密
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        //username不可重复
        SysUser sysUser1 = sysUserMapper.selectById(sysUser.getUsername());
        if (sysUser1!=null){
            throw new BoCaiException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //给密码加密
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //根据用户id，删除id之前分配角色数据
        sysUserRoleMapper.deleteByUserId(assginRoleDto.getUserId());
        //2.重新分配新数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(assginRoleDto.getUserId());
        //遍历的到每个角色的id
        sysUserRoleMapper.bathInsetRole(assginRoleDto.getUserId(),roleIdList);
    }
}
