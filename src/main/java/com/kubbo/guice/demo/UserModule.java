package com.kubbo.guice.demo;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

/**
 * Created by zw86077 on 2016/4/23.
 */
public class UserModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Repository.class).to(MysqlRepository.class);
        bind(UserService.class).to(UserServiceImpl.class);
        bind(String.class).annotatedWith(Names.named("serviceName")).toInstance("jdbc");
    }


    @Provides
    @Named("moduleId")
    public String id() {
        return "useModuleId";
    }
}
