package top.woaibocai.bczx.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import top.woaibocai.bczx.common.anno.EnableUserWebMvcConfiguration;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-31 17:20
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"top.woaibocai.bczx"})
@EnableUserWebMvcConfiguration
public class UserApplication8512 {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication8512.class,args);
    }
}
