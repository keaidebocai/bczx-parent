package top.woaibocai.bczx.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.OrderStatisticsMapper;
import top.woaibocai.bczx.model.dto.order.OrderStatisticsDto;
import top.woaibocai.bczx.model.entity.order.OrderStatistics;
import top.woaibocai.bczx.model.vo.order.OrderStatisticsVo;
import top.woaibocai.bczx.service.OrderInfoService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-26 17:38
 **/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private OrderStatisticsMapper orderStatisticsMapper;
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        //根据dto条件查询统计结果数据，返回list集合
        List<OrderStatistics> statisticsList = orderStatisticsMapper.selectListByTime(orderStatisticsDto);
        //遍历list集合，得到所有日期，把所有日期封装到新集合里
        List<String> dateList = statisticsList.stream()
                .map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList());
        //把遍历list集合，得到所有日期对应总金额，把总金额封装list新集合中
        List<BigDecimal> amountList = statisticsList.stream()
                .map(OrderStatistics::getTotalAmount).collect(Collectors.toList());
        //把两个list集合封装到OrderStaisticsVo,返回OrderStatisticsVo
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);
        return orderStatisticsVo;
    }
}
