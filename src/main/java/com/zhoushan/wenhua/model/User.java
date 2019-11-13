package com.zhoushan.wenhua.model;

public class User {
    private Integer id;
    private String username;
    private String path;
    private String gxqm;
    private String likelist;
    private String perms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGxqm() {
        return gxqm;
    }

    public void setGxqm(String gxqm) {
        this.gxqm = gxqm;
    }

    public String getLikelist() {
        return likelist;
    }

    public void setLikelist(String likelist) {
        this.likelist = likelist;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    private String password;
private String logopath;

    public String getLogopath() {
        return logopath;
    }

    public void setLogopath(String logopath) {
        this.logopath = logopath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", path='" + path + '\'' +
                ", gxqm='" + gxqm + '\'' +
                ", likelist='" + likelist + '\'' +
                ", perms='" + perms + '\'' +
                '}';
    }
}
