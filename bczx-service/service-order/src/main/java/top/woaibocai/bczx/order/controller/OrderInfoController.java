package top.woaibocai.bczx.order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.woaibocai.bczx.order.service.OrderInfoService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-02 16:25
 **/
@Tag(name = "订单管理")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;


}
