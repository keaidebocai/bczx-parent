package top.woaibocai.bczx.model.entity.order;


import lombok.Data;
import top.woaibocai.bczx.model.entity.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatistics extends BaseEntity {

    private Date orderDate;
    private BigDecimal totalAmount;
    private Integer totalNum;
    
}