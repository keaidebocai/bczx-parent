package top.woaibocai.bczx.order.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import top.woaibocai.bczx.model.dto.h5.OrderInfoDto;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.vo.common.Result;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.h5.TradeVo;
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
    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId,ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "确认下单")
    @GetMapping("auth/trade")
    public Result trade(){
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result getOrderinfo(@PathVariable Long orderId){
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo,ResultCodeEnum.SUCCESS);
    }
}
