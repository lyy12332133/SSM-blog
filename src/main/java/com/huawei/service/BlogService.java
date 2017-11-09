package com.huawei.service;

import com.huawei.domain.Blog;

import java.util.List;

/**
 * Created by dllo on 17/11/8.
 */
public interface BlogService {
    List<Blog> findByUid(Integer id);

    Blog findSingle(Integer id);

    int addBlog(Blog blog);
}
