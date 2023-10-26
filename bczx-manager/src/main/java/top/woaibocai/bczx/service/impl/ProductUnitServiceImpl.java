package top.woaibocai.bczx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.ProductUnitMapper;
import top.woaibocai.bczx.model.entity.base.ProductUnit;
import top.woaibocai.bczx.service.ProductUnitService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-26 12:35
 **/
@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Resource
    private ProductUnitMapper productUnitMapper;
    @Override
    public List<ProductUnit> findAll() {
        LambdaQueryWrapper<ProductUnit> wrapper = new LambdaQueryWrapper<>();
        return productUnitMapper.selectList(null);
    }
}
