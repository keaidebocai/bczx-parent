package top.woaibocai.bczx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import top.woaibocai.bczx.properties.MinioProperties;
import top.woaibocai.bczx.properties.UserProperties;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-19 14:44
 **/
@SpringBootApplication
@ComponentScan("top.woaibocai")
//使UserProperties的@ConfigurationProperties注解生效
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
@EnableScheduling
public class ManagerApplication8501 {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication8501.class,args);
    }
}
