package top.woaibocai.bczx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.ProductSpecMapper;
import top.woaibocai.bczx.model.entity.product.ProductSpec;
import top.woaibocai.bczx.service.ProductSpecService;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-25 10:20
 **/
@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    @Resource
    private ProductSpecMapper productSpecMapper;
    @Override
    public IPage<ProductSpec> findByPage(Integer page, Integer limit) {
        IPage<ProductSpec> iPage = new Page<>();
        iPage.setCurrent(page);
        iPage.setSize(limit);
        IPage<ProductSpec> specIPage = productSpecMapper.selectPage(iPage, null);
        return specIPage;
    }

    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.insert(productSpec);
    }

    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.updateById(productSpec);
    }

    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.selectList(null);
    }
}
