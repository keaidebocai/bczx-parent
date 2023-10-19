package top.woaibocai.bczx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @program: bczx-parent
 * @description: 读取配置文件的值
 * @author: woaibocai
 * @create: 2023-10-19 21:55
 **/
@Data
@ConfigurationProperties(prefix = "bczx.auth")
public class UserProperties {
    private List<String> noAuthUrls;
}
