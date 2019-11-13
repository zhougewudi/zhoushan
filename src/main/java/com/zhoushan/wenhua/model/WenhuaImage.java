package com.zhoushan.wenhua.model;

public class WenhuaImage {

    private Integer id;
    private Integer iswhose;
    private String path;
    private String desc;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "WenhuaImage{" +
                "id=" + id +
                ", iswhose=" + iswhose +
                ", path='" + path + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
