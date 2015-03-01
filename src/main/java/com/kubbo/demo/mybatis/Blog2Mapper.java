package com.kubbo.demo.mybatis;

import org.apache.ibatis.annotations.Select;

/**
 * Created by zhuwei on 2015/3/1.
 */

public interface Blog2Mapper {

    //如果只有一个参数的话，名字可以乱写
    @Select("SELECT * FROM blog WHERE ID = #{id}")
    Blog select(int id);


}
