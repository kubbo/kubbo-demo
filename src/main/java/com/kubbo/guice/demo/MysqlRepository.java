package com.kubbo.guice.demo;

/**
 * Created by zw86077 on 2016/4/23.
 */
public class MysqlRepository implements Repository {
    @Override
    public Object select(int id) {
        return "Hello world";
    }
}
