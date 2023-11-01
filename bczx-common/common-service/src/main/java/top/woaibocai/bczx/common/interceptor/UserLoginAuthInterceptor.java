package top.woaibocai.bczx.common.interceptor;

import com.alibaba.fastjson.JSON;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import top.woaibocai.bczx.model.entity.user.UserInfo;
import top.woaibocai.bczx.utils.AuthContextUtil;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-01 11:19
 **/
public class UserLoginAuthInterceptor implements HandlerInterceptor {
    @Resource
    public RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = request.getHeader("token");
        String userJson = redisTemplate.opsForValue().get("user:bczx:" + token);
        //放到threadlocal里
        AuthContextUtil.setUserInfo(JSON.parseObject(userJson, UserInfo.class));
        return true;
    }
}
