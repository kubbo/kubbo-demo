package com.kubbo.guice.demo;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Created by zw86077 on 2016/4/23.
 */
public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new UserModule());
        UserService service = injector.getInstance(UserService.class);
        System.out.println(service.getUser(1));

    }
}
