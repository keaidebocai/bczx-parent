package top.woaibocai.bczx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import top.woaibocai.bczx.model.dto.product.ProductDto;
import top.woaibocai.bczx.model.entity.product.Product;

public interface ProductService {

    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);
}
