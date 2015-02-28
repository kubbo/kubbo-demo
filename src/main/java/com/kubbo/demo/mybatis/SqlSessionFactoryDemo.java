package com.kubbo.demo.mybatis;

import junit.framework.TestCase;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by zhuwei on 2015/2/25.
 */
public class SqlSessionFactoryDemo extends TestCase {

    private SqlSessionFactory sqlSessionFactory;

    @Override
    protected void setUp() throws Exception {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mybatis/mybatis-config.xml");

        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
    }


    /**
     * 以 mybatis-config.xml创建session,根据 mapper 配置来操作
     */
    public void testSelectOne() {
        SqlSession session = sqlSessionFactory.openSession();
        try {

            //这里的statement 可以写全称(namespace+id)或者简称(id)
            Object o = session.selectOne("com.kubbo.demo.mybatis.BlogMapper.selectBlog", 1);
            System.out.println(o);
            Object o2 = session.selectOne("selectBlog", 1);
            System.out.println(o2);

        } finally {
            session.close();
        }
    }


    /**
     * 获取mapper 的代理
     * Mapper 作用域
     * Mapper是由session 中生成的，所以mapper 的作用域同session 一样的，同样都是非线程安全的
     * session.getMapper 每次都会生成一个新的 mapper 实例
     */
    public void testMapper() {
        SqlSession session = sqlSessionFactory.openSession();

        try {
            BlogMapper mapper1 = session.getMapper(BlogMapper.class);
            BlogMapper mapper2 = session.getMapper(BlogMapper.class);
            System.out.println(Thread.currentThread().getName() + "-mapper1:" + mapper1);
            System.out.println(Thread.currentThread().getName() + "-mapper2:" + mapper2);
            new Thread(() -> {
                SqlSession s = sqlSessionFactory.openSession();
                BlogMapper m1 = s.getMapper(BlogMapper.class);
                BlogMapper m2 = s.getMapper(BlogMapper.class);

                System.out.println(Thread.currentThread().getName() + "-mapper1:" + m1);
                System.out.println(Thread.currentThread().getName() + "-mapper2:" + m2);

            }).start();
            Thread.sleep(1000);


            Blog blog = mapper1.selectBlog(1);
            System.out.println(blog);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            session.close();

        }
    }


    /**
     * mapUnderscoreToCamelCase 是否开启自动驼峰命令规则的映射,默认false
     * 如数据库:create_time
     * Model:createTime
     */
    public void testMapUnderscoreToCamelCase() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = mapper.selectBlog(1);
        System.out.println(blog.getCreateTime());
    }


    public void testSelectRecentBlog() {
        BlogMapper mapper = sqlSessionFactory.openSession().getMapper(BlogMapper.class);
        Blog blog = mapper.selectRecentBlog();
        System.out.println(blog);
    }


    public void testDelete() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog = mapper.selectRecentBlog();
        int i = mapper.deleteBlog(blog.getId());
        sqlSession.commit();
        System.out.println(i);
    }


    public void testCacheEnabled() throws IOException, InterruptedException {
        SqlSession session = sqlSessionFactory.openSession();
        int id = 1;
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        //只会进行一次数据库的查询，第二次直接取缓存
        System.out.println(mapper.selectRecentBlog());
        session.commit();
        //如果执行commit,一级缓存也会失效,此时会再次执行向数据库中取数据
        System.out.println(mapper.selectRecentBlog());

        //由于缓存中没有此查询，所以还会去数据中进行查询的
        System.out.println(mapper.selectBlog(3));


    }


    public void testInsertBlog() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setTitle("hello");
        blog.setContent("world");
        blog.setCreateTime(new Date());
        int i = mapper.insertBlog(blog);
        System.out.println(blog.getId());
        System.out.println(i);
    }

    public void testTypeHandler() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setTitle("test");
        blog.setContent("test2");
        blog.setCreateTime(new Date());
        blog.setBlogType(BlogType.C);
        int i = mapper.insertBlog(blog);
        session.commit();
        System.out.println(i);

    }


    public void testPluginHandler() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = mapper.selectRecentBlog();
        System.out.println(blog);
    }


    public void testUpdateBlog() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = mapper.selectRecentBlog();
        if (blog != null) {
            blog.setId(12);
            blog.setTitle("zhu");
            blog.setContent("wei");
            blog.setCreateTime(new Date());
            int i = mapper.updateBlog(blog);
            session.commit();

            System.out.println(i);
        }

    }

    public void testSelectByCondition() {
        SqlSession session = sqlSessionFactory.openSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog condition = new Blog();
        condition.setId(1);
        Blog blog = mapper.selectByCondition(condition);
        System.out.println(blog);

    }
}
