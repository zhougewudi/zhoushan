package com.zhoushan.wenhua.model;

public class Href {

    private Integer id;
    private Integer iswhose;
    private String content;
    private String des;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIswhose() {
        return iswhose;
    }

    public void setIswhose(Integer iswhose) {
        this.iswhose = iswhose;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    @Override
    public String toString() {
        return "Href{" +
                "id=" + id +
                ", iswhose=" + iswhose +
                ", content='" + content + '\'' +
                ", des='" + des + '\'' +
                '}';
    }
}
