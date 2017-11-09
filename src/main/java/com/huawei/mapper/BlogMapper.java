package com.huawei.mapper;

import com.huawei.domain.Blog;

import java.util.List;

/**
 * Created by dllo on 17/11/8.
 */
public interface BlogMapper {

    List<Blog> findByUid(int userId);

    Blog findSingle(int id);

    int addBlog(Blog blog);

    int deleteBlog(Blog blog);

    List<Blog> findByTitle(Blog blog);

    List<Blog> findByDes(Blog blog);

    List<Blog> findByContent(Blog blog);
}
