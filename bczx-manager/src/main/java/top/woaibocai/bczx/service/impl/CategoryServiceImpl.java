package top.woaibocai.bczx.service.impl;


import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.listener.ExcelListener;
import top.woaibocai.bczx.mapper.CategoryMapper;
import top.woaibocai.bczx.model.entity.product.Category;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.model.vo.product.CategoryExcelVo;
import top.woaibocai.bczx.service.CategoryService;
import top.woaibocai.bczx.utils.BeanCopyUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-24 10:46
 **/
@Service
public class CategoryServiceImpl implements CategoryService{
    @Resource
    private CategoryMapper categoryMapper;
    @Override
    public List<Category> findCategoryList(Long id) {
        // TODO 用stream流复用AllList实现，杜绝循环里查询
        // 根据分类id查询它下面的所有的子分类数据
        List<Category> categoryList = categoryMapper.selectByParentId(id);
        if(!CollectionUtils.isEmpty(categoryList)) {
            // 遍历分类的集合，获取每一个分类数据
            categoryList.forEach(item -> {
                // 查询该分类下子分类的数量
                int count = categoryMapper.countByParentId(item.getId());
                if(count > 0) {
                    item.setHasChildren(true);
                } else {
                    item.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try{
            //1.设置响应的头信息和其他信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //这里URLEncoder.encode可以防止中文乱码 和easyexecl没关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            //设置响应头
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //2.调用Mapper方法查询所有的分类，返回list集合
            List<Category> categoryList = categoryMapper.findAll();
            List<CategoryExcelVo> categoryExcelVos = BeanCopyUtils.copyBeanList(categoryList, CategoryExcelVo.class);
            //3.调用easyExecl的write方法完成写操作
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class)
                    .sheet("分类数据").doWrite(categoryExcelVos);
        }catch (Exception e){
            e.printStackTrace();
            throw new BoCaiException(ResultCodeEnum.DATA_ERROR);
        }
    }

    @Override
    public void importData(MultipartFile file) {
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener(categoryMapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class,excelListener)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BoCaiException(ResultCodeEnum.DATA_ERROR);
        }
    }


}
