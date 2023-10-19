package top.woaibocai.bczx.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.vo.system.ValidateCodeVo;
import top.woaibocai.bczx.service.ValidateCodeService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: bczx-parent
 * @description: 图形验证码
 * @author: woaibocai
 * @create: 2023-10-19 18:32
 **/
@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public ValidateCodeVo generateValidateCode() {
        //1.通过hutool工具生成图片的验证嘛
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 2);
        String code = circleCaptcha.getCode();
        String imageBase64 = circleCaptcha.getImageBase64();//返回图片验证嘛
        //2.把验证嘛存在redis里，设置redis的key value
        //设置数据的过期时间
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:validate"+ key,code ,5, TimeUnit.MINUTES);
        //3.返回ValidateCodeVo对象
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
