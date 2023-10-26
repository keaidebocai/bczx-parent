package top.woaibocai.bczx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.woaibocai.bczx.model.dto.order.OrderStatisticsDto;
import top.woaibocai.bczx.model.entity.order.OrderStatistics;

import java.util.List;

public interface OrderStatisticsMapper extends BaseMapper<OrderStatistics> {
    void insertByTask(OrderStatistics orderStatistics);

    List<OrderStatistics> selectListByTime(OrderStatisticsDto orderStatisticsDto);
}
