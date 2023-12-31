package top.woaibocai.bczx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.order.OrderInfo;
import top.woaibocai.bczx.model.entity.order.OrderItem;

import java.util.List;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    OrderInfo selectOrederById(Long orderId);

    List<OrderInfo> findOrderPage(Long userId, Integer orderStatus);

    List<OrderItem> findOrderId(Long id);

    OrderInfo getOrderInfoByOrderNo(String orderNo);
}
