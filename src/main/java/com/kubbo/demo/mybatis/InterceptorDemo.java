package com.kubbo.demo.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * Created by zhuwei on 2015/2/28.
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class})})
public class InterceptorDemo implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorDemo.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info("intercept");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        logger.info("plugin");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        logger.info("setProperties");

    }
}
