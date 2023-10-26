package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.entity.order.OrderStatistics;


public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    OrderStatistics selectOrderStatisticByDate(String createDate);
}
