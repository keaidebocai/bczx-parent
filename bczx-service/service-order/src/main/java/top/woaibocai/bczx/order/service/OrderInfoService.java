package top.woaibocai.bczx.order.service;

import top.woaibocai.bczx.model.dto.h5.OrderInfoDto;
import top.woaibocai.bczx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo getTrade();

    Long submitOrder(OrderInfoDto orderInfoDto);
}
