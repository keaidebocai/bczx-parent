package top.woaibocai.bczx.pay.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-03 17:58
 **/
@Data
@ConfigurationProperties(prefix = "bczx.alipay")
public class AlipayProperties {

    private String alipayUrl;
    private String appPrivateKey;
    public  String alipayPublicKey;
    private String appId;
    public  String returnPaymentUrl;
    public  String notifyPaymentUrl;

    public final static String format="json";
    public final static String charset="utf-8";
    public final static String sign_type="RSA2";
}
