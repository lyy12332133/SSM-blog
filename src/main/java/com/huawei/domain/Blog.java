package com.huawei.domain;

/**
 * Created by dllo on 17/11/8.
 */
public class Blog {
    private int id;
    private String title;
    private String des;
    private String content;
    private int userId;

    public Blog() {
    }

    public Blog(int id, String title, String des, String content, int userId) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.content = content;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", des='" + des + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }

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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
