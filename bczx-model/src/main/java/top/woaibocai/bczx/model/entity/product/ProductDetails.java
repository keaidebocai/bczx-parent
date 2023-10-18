package top.woaibocai.bczx.model.entity.product;

import top.woaibocai.bczx.model.entity.base.BaseEntity;
import lombok.Data;

@Data
public class ProductDetails extends BaseEntity {

	private Long productId;
	private String imageUrls;

}