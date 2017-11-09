package com.huawei.mapper;

import com.huawei.domain.User;

/**
 * Created by dllo on 17/11/8.
 */
public interface UserMapper {
    /**
     * 根据用户名获取用户对象
     * @param username 查询的用户名
     * @return 用户对象
     */
    User findByName(String username);

    User findByUser(User user);

    int register(User user);
}
