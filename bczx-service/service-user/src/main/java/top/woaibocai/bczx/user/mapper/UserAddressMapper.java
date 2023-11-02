package top.woaibocai.bczx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.user.UserAddress;

import java.util.List;

@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    List<UserAddress> findUserAddressByUserId(Long userId);
}
