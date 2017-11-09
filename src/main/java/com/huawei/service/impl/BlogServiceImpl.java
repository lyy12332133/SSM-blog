package com.huawei.service.impl;


import com.huawei.domain.Blog;
import com.huawei.mapper.BlogMapper;
import com.huawei.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dllo on 17/11/8.
 */
@Service("blogService")
public class BlogServiceImpl implements BlogService{

    @Autowired
    @Qualifier("blogMapper")
    private BlogMapper blogMapper;

    public List<Blog> findByUid(Integer id) {
        return blogMapper.findByUid(id);
    }

    public Blog findSingle(Integer id) {
        return blogMapper.findSingle(id);
    }

    public int addBlog(Blog blog) {
        return blogMapper.addBlog(blog);
    }
}
