package top.woaibocai.bczx.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.woaibocai.bczx.model.entity.system.SysUser;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.utils.AuthContextUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @program: bczx-parent
 * @description: 实现拦截器，在后台系统中，每一个请求都要检查是否含有token~
 * @author: woaibocai
 * @create: 2023-10-19 21:20
 **/
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取请求方式
        //如果是options 遇见请求，直接放行
        String method = request.getMethod();
        if ("OPTIONS".equals(method)){
            return true;
        }
        //2.从请求头上获取token
        String token = request.getHeader("token");
        //3.如果token为空，返回错误信息提示
        if (StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }
        //4.如果token不为空，拿着token查询redis
        String userInfoString = redisTemplate.opsForValue().get("user:login" + token);
        //5.如果redis查不到数据，返回错误信息
        if (StrUtil.isEmpty(userInfoString)){
            responseNoLoginInfo(response);
            return false;
        }
        //6.如果redis查询用户信息，把用户信息放在threadLocal
        SysUser sysUser = JSON.parseObject(userInfoString, SysUser.class);
        AuthContextUtil.set(sysUser);
        //7.把redis用户信息数据更新过期时间
        redisTemplate.expire("user:login" + token,30, TimeUnit.MINUTES);
        //放行
        return true;
    }
    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //threadLocal删除
        AuthContextUtil.remove();
    }
}
