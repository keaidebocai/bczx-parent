package top.woaibocai.bczx.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import org.apache.poi.ss.formula.functions.T;
import top.woaibocai.bczx.mapper.CategoryMapper;
import top.woaibocai.bczx.model.vo.product.CategoryExcelVo;

import java.util.List;


/**
 * @program: bczx-parent
 * @description: 监听器
 * @author: woaibocai
 * @create: 2023-10-24 15:09
 **/

public class ExcelListener<T> implements ReadListener<T> {
    //每隔5条存储数据库，实际使用中可以100条，然后清理list，方便内存回收
    private static final int BATCH_COUNT = 100;
    //缓存数据
    private List<T> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    //构造传递mapper，操作数据库
    private CategoryMapper categoryMapper;
    public ExcelListener(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void invoke(T t, AnalysisContext analysisContext) {
        cachedDataList.add(t);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //保存数据
        saveData();
    }
    //保存的方法
    private void saveData() {
        categoryMapper.batchInsert((List<CategoryExcelVo>) cachedDataList);
    }
}
