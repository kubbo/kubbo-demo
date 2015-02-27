package com.kubbo.demo.mybatis;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by zhuwei on 2015/2/26.
 */
public class Blog implements Serializable {

    private int id;
    private String title;
    private String content;
    private Date createTime;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
