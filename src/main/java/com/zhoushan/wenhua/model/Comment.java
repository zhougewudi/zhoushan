package com.zhoushan.wenhua.model;

public class Comment {

    private Integer id;
    private Integer iswhose;
    private Integer towhose;
    private String content;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public Integer getTowhose() {
        return towhose;
    }

    public void setTowhose(Integer towhose) {
        this.towhose = towhose;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", iswhose=" + iswhose +
                ", towhose=" + towhose +
                ", content='" + content + '\'' +
                '}';
    }
}
