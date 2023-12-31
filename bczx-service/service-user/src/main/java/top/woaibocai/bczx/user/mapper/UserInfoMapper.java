package top.woaibocai.bczx.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.woaibocai.bczx.model.entity.user.UserInfo;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo selectUserInfoByUserName(String username);
}
