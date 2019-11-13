package com.zhoushan.wenhua.model;

import java.util.Date;

public class Article {

    private Integer id;
    private String kind;
    private Integer count;
    private String content;
    private Integer good;
    private Integer iswhose;
    private String title;
    private String time;
    private Integer comments;
    private String authName;


    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }



    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getGood() {
        return good;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public Integer getIswhose() {
        return iswhose;
    }

    public void setIswhose(Integer iswhose) {
        this.iswhose = iswhose;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", kind=" + kind +
                ", count=" + count +
                ", content='" + content + '\'' +
                ", good=" + good +
                ", iswhose=" + iswhose +
                ", title='" + title + '\'' +
                '}';
    }
}
