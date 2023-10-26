package top.woaibocai.bczx.service;

import top.woaibocai.bczx.model.dto.order.OrderStatisticsDto;
import top.woaibocai.bczx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
