package com.kubbo.demo.mybatis;

import junit.framework.TestCase;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

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
}
