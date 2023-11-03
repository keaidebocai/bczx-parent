package top.woaibocai.bczx.user.service.impl;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.woaibocai.bczx.model.entity.user.UserAddress;
import top.woaibocai.bczx.user.mapper.UserAddressMapper;
import top.woaibocai.bczx.user.service.UserAddressService;
import top.woaibocai.bczx.utils.AuthContextUtil;

import java.util.List;

/**
 * @program: bczx-parent
 * @description:
 * @author: woaibocai
 * @create: 2023-11-02 16:19
 **/
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;
    @Override
    public List<UserAddress> findUserAddressList() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        List<UserAddress> userAddressList = userAddressMapper.findUserAddressByUserId(userId);
        return userAddressList;
    }

    @Override
    public UserAddress getUserAddress(Long id) {
        return userAddressMapper.selectById(id);
    }
}
