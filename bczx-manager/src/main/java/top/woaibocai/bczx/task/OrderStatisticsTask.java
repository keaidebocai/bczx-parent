package top.woaibocai.bczx.task;

import cn.hutool.core.date.DateUtil;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.woaibocai.bczx.mapper.OrderInfoMapper;
import top.woaibocai.bczx.mapper.OrderStatisticsMapper;
import top.woaibocai.bczx.model.entity.order.OrderStatistics;

import java.util.Date;

/**
 * @program: bczx-parent
 * @description: 定时任务
 * @author: woaibocai
 * @create: 2023-10-26 16:35
 **/
@Component
public class OrderStatisticsTask {
    @Resource
    private OrderStatisticsMapper orderStatisticsMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Scheduled(cron = "0 0 2 * * ?")
    public void OrderTotalAmountStatistics(){
        //1.获取前一天的日期
        String createDate = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        //2.根据前一天日期进行统计功能（分组操作）
        //同意前一天的交易金额
        OrderStatistics orderStatistics =
                orderInfoMapper.selectOrderStatisticByDate(createDate);
        System.out.println(orderStatistics);
        //3。吧统计之后的数据，添加到结果表中
        if (orderStatistics != null){
            orderStatisticsMapper.insertByTask(orderStatistics);
        }
    }
}