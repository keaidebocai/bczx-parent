package top.woaibocai.bczx.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.entity.product.Brand;
import top.woaibocai.bczx.product.mapper.BrandMapper;
import top.woaibocai.bczx.product.service.BrandService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-30 18:03
 **/
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;
    @Override
    public List<Brand> findAll() {
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Brand::getId);
        List<Brand> list = brandMapper.selectList(wrapper);
        return list;
    }
}
