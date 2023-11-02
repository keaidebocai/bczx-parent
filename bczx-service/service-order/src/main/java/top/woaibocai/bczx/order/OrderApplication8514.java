package top.woaibocai.bczx.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.woaibocai.bczx.common.anno.EnableUserTokenFeignInterceptor;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-02 16:23
 **/
@SpringBootApplication
@EnableFeignClients(basePackages = {"top.woaibocai.bczx.feign"})
//把token在远程调用的过程中携带上
@EnableUserTokenFeignInterceptor
public class OrderApplication8514 {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication8514.class,args);
    }
}
