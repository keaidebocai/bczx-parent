package top.woaibocai.bczx.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: bczx-parent
 * @description: Knife4j配置
 * @author: woaibocai
 * @create: 2023-10-19 11:39
 **/
@Configuration
public class Knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
        return GroupedOpenApi.builder()
                .group("admin-api")         // 分组名称
                .pathsToMatch("/admin/**")  // 接口请求路径规则
                .build();
    }

    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("菠菜甑选API接口文档")
                        .version("1.0")
                        .description("菠菜甑选API接口文档")
                        .contact(new Contact().name("woaibocai"))); // 设定作者
    }
}
