package top.woaibocai.bczx.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.common.exception.BoCaiException;
import top.woaibocai.bczx.mapper.SysMenuMapper;
import top.woaibocai.bczx.model.entity.system.SysMenu;
import top.woaibocai.bczx.model.other.SysMenuTree;
import top.woaibocai.bczx.model.vo.common.ResultCodeEnum;
import top.woaibocai.bczx.service.SysMenuService;
import top.woaibocai.bczx.utils.BeanCopyUtils;
import top.woaibocai.bczx.utils.MenuHelper;
import top.woaibocai.bczx.utils.MenuHelperByStream;


import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-10-23 11:07
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenuTree> findNodes() {
        //查询所有菜单，返回list集合
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSortValue);
        List<SysMenu> list = sysMenuMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        List<SysMenuTree> copyBeanList = BeanCopyUtils.copyBeanList(list, SysMenuTree.class);
        long startTime=System.nanoTime(); //获取开始时间
        //2.调用工具类的方法，把返回的集合按照要求封装
        List<SysMenuTree> treeList = MenuHelper.buildTree(copyBeanList);
//        List<SysMenuTree> treeList = MenuHelperByStream.buildTree(copyBeanList);
        long endTime=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(endTime-startTime)+"ns");//输出程序运行时间
        return treeList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
    }

    @Override
    public void update(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        //先判断是否有子菜单
        //有，无法山，用自己的id尊为parent_id查询
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(SysMenu::getParentId,id);
        Long count = sysMenuMapper.selectCount(wrapper);
        if (count > 0){
            throw new BoCaiException(ResultCodeEnum.NODE_ERROR);
        }
        //无，删
        sysMenuMapper.deleteById(id);
    }
}
