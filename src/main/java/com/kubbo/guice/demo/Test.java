package com.kubbo.guice.demo;

import java.io.File;

/**
 * Created by zw86077 on 2016/4/25.
 */
public class Test {

    public static void main(String[] args) {
        String dir = "\\var\\druid\\task\\index_realtime_trend_2016-04-22T08:30:00.000Z_0_0\\work\\persist\\trend\\2016-04-25T22:00:00.000+08:00_2016-04-25T22:15:00.000+08:00\\0";


        File file = new File(dir);
        file.mkdirs();

        System.out.println(file.exists());

    }
}
