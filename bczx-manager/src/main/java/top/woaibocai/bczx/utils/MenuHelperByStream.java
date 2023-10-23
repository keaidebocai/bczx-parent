package top.woaibocai.bczx.utils;


import top.woaibocai.bczx.model.other.SysMenuTree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: bczx-parent
 * @description: 用stream来处理封装树形菜单数据
 * @author: woaibocai
 * @create: 2023-10-23 13:36
 **/
public class MenuHelperByStream {
    public static List<SysMenuTree> buildTree(List<SysMenuTree> sysMenuList){
        ArrayList<SysMenuTree> trees = new ArrayList<>();
        sysMenuList.stream()
                .filter(m -> m.getParentId()==0)
                .map(m -> trees.add(findChildrenByStream(m,sysMenuList)))
                .collect(Collectors.toList());
        return trees;
    }
    private static SysMenuTree findChildrenByStream(SysMenuTree m, List<SysMenuTree> sysMenuList) {
        m.setChildren(new ArrayList<>());
        sysMenuList.stream()
                .filter(it -> m.getId() == it.getParentId())
                .map(it -> m.getChildren().add(findChildrenByStream(it,sysMenuList)))
                .collect(Collectors.toList());
        return m;
    }


}
