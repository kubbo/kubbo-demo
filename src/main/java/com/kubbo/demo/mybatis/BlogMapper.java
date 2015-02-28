package com.kubbo.demo.mybatis;

/**
 * Created by zhuwei on 2015/2/26.
 */
public interface BlogMapper {


    Blog selectBlog(int id);

    Blog selectRecentBlog();

    int deleteBlog(int id);

    int insertBlog(Blog blog);

    int updateBlog(Blog blog);
}
