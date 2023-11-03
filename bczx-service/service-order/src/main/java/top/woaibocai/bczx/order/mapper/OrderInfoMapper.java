package top.woaibocai.bczx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.order.OrderInfo;

@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    OrderInfo selectOrederById(Long orderId);
}
