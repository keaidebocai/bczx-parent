package top.woaibocai.bczx.model.entity.system;

import top.woaibocai.bczx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "系统菜单实体类")
@Data
public class SysMenu extends BaseEntity {
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


}