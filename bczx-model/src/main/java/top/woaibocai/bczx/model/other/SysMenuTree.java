package top.woaibocai.bczx.model.other;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.woaibocai.bczx.model.entity.system.SysMenu;

import java.util.List;

/**
 * @program: bczx-parent
 * @description: 用来封装树形结构
 * @author: woaibocai
 * @create: 2023-10-23 14:28
 **/
@Schema(description = "系统菜单Tree实体类")
@Data
public class SysMenuTree {
    @Schema(description = "id")
    private Long id;
    @Schema(description = "父节点id")
    private Long parentId;

    @Schema(description = "节点标题")
    private String title;

    @Schema(description = "组件名称")
    private String component;

    @Schema(description = "排序值")
    private Integer sortValue;

    @Schema(description = "状态(0:禁止,1:正常)")
    private Integer status;

    // 下级列表
    @Schema(description = "子节点")
    private List<SysMenuTree> children;
}
