package top.woaibocai.bczx.user.service;

import top.woaibocai.bczx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddressList();

    UserAddress getUserAddress(Long id);
}
