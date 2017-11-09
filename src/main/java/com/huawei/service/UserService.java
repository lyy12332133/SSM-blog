package com.huawei.service;

import com.huawei.domain.User;

/**
 * Created by dllo on 17/11/8.
 */
public interface UserService {
    /**
     * 根据用户名获取用户对象
     * @param username 查询的用户名
     * @return 用户对象
     */
    User findByName(String username);
}
