package top.woaibocai.bczx.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.order.OrderItem;

@Mapper
public interface OrderItemMapper extends BaseMapper<OrderItem> {
}
