package top.woaibocai.bczx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.BrandMapper;
import top.woaibocai.bczx.model.entity.product.Brand;
import top.woaibocai.bczx.service.BrandService;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-24 16:07
 **/
@Service
public class BrandServiceImpl implements BrandService {
    @Resource
    private BrandMapper brandMapper;
    @Override
    public IPage<Brand> findByPage(Integer page, Integer limit) {
        IPage<Brand> iPage = new Page<>();
        iPage.setPages(page);
        iPage.setSize(limit);
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Brand::getId);
        IPage<Brand> brandIPage = brandMapper.selectPage(iPage, wrapper);
        return brandIPage;
    }

    @Override
    public void save(Brand brand) {
        brandMapper.insert(brand);
    }
}
