package top.woaibocai.bczx.common.anno;

import org.springframework.context.annotation.Import;
import top.woaibocai.bczx.common.feign.UserTokenOpenFeignInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
@Import(value = UserTokenOpenFeignInterceptor.class)
public @interface EnableUserTokenFeignInterceptor {
}
