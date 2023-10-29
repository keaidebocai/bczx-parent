package top.woaibocai.bczx.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-27 16:35
 **/
@SpringBootApplication
//开启缓存
@EnableCaching
public class ProductApplication8511 {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication8511.class,args);
    }
}
