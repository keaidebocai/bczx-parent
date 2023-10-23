package top.woaibocai.bczx.utils;
import top.woaibocai.bczx.model.other.SysMenuTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: bczx-parent
 * @description: 封装树形菜单数据
 * @author: woaibocai
 * @create: 2023-10-23 11:31
 **/
public class MenuHelper {
    //递归实现封装过程
    public static List<SysMenuTree> buildTree(List<SysMenuTree> sysMenuList){
        //sysMenuList所有菜单集合
        //创建list集合，用于封装最终的数据
        ArrayList<SysMenuTree> trees = new ArrayList<>();
        //遍历所有集合，得到每一个sysMenu
        for (SysMenuTree sysMenuTree:sysMenuList){
            //找到递归操作的入口，第一层的菜单，也就是parent_id = 0
            if (sysMenuTree.getParentId() == 0){
                //根据第一层，找下层数据
                //写个方法实现找下层过程，第一个参数是等钱第一层菜单，第二个参数是所有菜单集合
                trees.add(findChildren(sysMenuTree,sysMenuList));
            }
        }
        return trees;
    }
    private static SysMenuTree findChildren(SysMenuTree sysMenuTree, List<SysMenuTree> sysMenuList) {
        //把父级 sysMenu 的 id 和 sysMenuList 的parent_id 遍历取交集；封装下一层；
        sysMenuTree.setChildren(new ArrayList<>());
        //把父级 sysMenu 的 id 和 sysMenuList 的parent_id 遍历取交集
        for (SysMenuTree it:sysMenuList){
            //判断id和parent_id是否相同;it是下层数据
            if (sysMenuTree.getId().longValue() == it.getParentId().longValue()){
                //it是下层数据，进行封装
                sysMenuTree.getChildren().add(findChildren(it,sysMenuList));
            }
        }
        return sysMenuTree;
    }
}
