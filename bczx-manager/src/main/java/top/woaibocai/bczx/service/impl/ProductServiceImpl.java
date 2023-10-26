package top.woaibocai.bczx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.mapper.ProductDetailsMapper;
import top.woaibocai.bczx.mapper.ProductMapper;
import top.woaibocai.bczx.mapper.ProductSkuMapper;
import top.woaibocai.bczx.model.dto.product.ProductDto;
import top.woaibocai.bczx.model.entity.product.Product;
import top.woaibocai.bczx.model.entity.product.ProductDetails;
import top.woaibocai.bczx.model.entity.product.ProductSku;
import top.woaibocai.bczx.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-25 16:14
 **/
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductSkuMapper productSkuMapper;
    @Resource
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page , limit) ;
        List<Product> productList =  productMapper.findByPage(productDto) ;
        return new PageInfo<>(productList);
    }

    @Override
    public void save(Product product) {
        //1.保存商品基本信息 produst表
        product.setStatus(0);
        product.setAuditStatus(0);
        productMapper.save(product);
        //2.获取商品sku列表及和，保存sku信息，product_ksu表
        List<ProductSku> productSkuList = product.getProductSkuList();
        for (int i=0;i<productSkuList.size();i++){
            ProductSku productSku = productSkuList.get(i);
            //商品编号
            productSku.setSkuCode(product.getId()+"_" + i);
            //商品id
            productSku.setProductId(product.getId());
            //skuName
            productSku.setSkuName(product.getName()+ productSku.getSkuSpec());
            productSku.setSaleNum(0);
            productSku.setStatus(0);
            productSkuMapper.save(productSku);
        }
        //3.保存商品详情数据 product_details表
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);

    }

    @Override
    public Product getById(Long id) {
        //根据id查询商品基本信息
        Product product = productMapper.selectById(id);
        //根据id查询商品sku新车型列表 product_sku
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSku::getProductId,id);
        List<ProductSku> productSkus = productSkuMapper.selectList(wrapper);
        product.setProductSkuList(productSkus);
        //根据id查询商品详情数据 product_details
        LambdaQueryWrapper<ProductDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDetails::getProductId,id);
        ProductDetails productDetails = productDetailsMapper.selectOne(queryWrapper);
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        //product表的数据
        productMapper.deleteById(id);
        //删除product_ske
        LambdaQueryWrapper<ProductSku> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductSku::getProductId,id);
        productSkuMapper.delete(wrapper);
        //根据product_details
        LambdaQueryWrapper<ProductDetails> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDetails::getProductId,id);
        productDetailsMapper.delete(queryWrapper);
    }

    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if (auditStatus == 1){
            product.setAuditStatus(1);
            product.setAuditMessage("审核通过");
        } else {
            product.setStatus(-1);
            product.setAuditMessage("审核不通过");
        }
        productMapper.updateById(product);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateById(product);
    }


}
