package top.woaibocai.bczx.order.service;

import com.github.pagehelper.PageInfo;
import top.woaibocai.bczx.model.dto.h5.OrderInfoDto;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus);
}
