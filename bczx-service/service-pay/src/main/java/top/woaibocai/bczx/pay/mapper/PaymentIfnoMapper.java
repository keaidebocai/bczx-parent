package top.woaibocai.bczx.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.pay.PaymentInfo;

@Mapper
public interface PaymentIfnoMapper extends BaseMapper<PaymentInfo> {
    PaymentInfo getByOrderNo(String orderNo);
}
