package top.woaibocai.bczx.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.woaibocai.bczx.common.anno.EnableUserWebMvcConfiguration;
import top.woaibocai.bczx.pay.utils.AlipayProperties;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-03 15:55
 **/
@SpringBootApplication
@EnableUserWebMvcConfiguration
@EnableFeignClients(basePackages = {"top.woaibocai.bczx.feign"})
@EnableConfigurationProperties(value = { AlipayProperties.class })
public class PayApplication8515 {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication8515.class,args);
    }
}
