package com.kubbo.guice.demo;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created by zw86077 on 2016/4/23.
 */
public class UserServiceImpl implements UserService {

    @Inject
    private Repository repository;

    @Inject
    @Named("serviceName")
    private String serviceName;


    @Inject
    @Named("moduleId")
    private String moduleId;
    @Override
    public String getUser(int id) {
        System.out.println("serviceName:" + serviceName);
        System.out.println("moduleId:"+moduleId);
        return repository.select(id).toString();
    }
}
