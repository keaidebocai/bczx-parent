package top.woaibocai.bczx.common.anno;

import org.springframework.context.annotation.Import;
import top.woaibocai.bczx.common.config.UserWebMvcConfiguration;
import top.woaibocai.bczx.common.interceptor.UserLoginAuthInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-01 11:32
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = { UserLoginAuthInterceptor.class , UserWebMvcConfiguration.class})
public @interface EnableUserWebMvcConfiguration {
}
