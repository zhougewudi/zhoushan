package com.zhoushan.wenhua.service;

import com.zhoushan.wenhua.model.*;

import java.util.List;
import java.util.Map;

public interface MyService {

    Wenhua selectWenhuaById(Integer id);
    List<WenhuaImage> selectImagesById(Integer id);
    List<Href> selectHrefsById(Integer id);
    List<Article> selectAllArticles();
    User selectUserByName(String username);
    void insertIntoArticle(Map<String,Object> map);
    Article selectArticleById(int id);
    List<Comment> selectCommentsById(int id);
    User selectUserById(int id);
    void insertIntoComment(Map<String,Object> map);
    void updateCountById(int id);
    void updataCommentsById(String str);
    List<Article> selectArticlesByType(String type);
    void updateWenhuaById(int id);
    List<Wenhua> selectWenhuaDesc();
    List<Wenhua> selectWenhuaByTag(Integer id);
    List<User> selectAllUsers();
    void updateWenhua(Map<String,Object> map);
    void insertIntoWenhua(Map<String,Object> map);
    void deleteSpecialById(int id);
    void insertIntowenhuImage(Map<String,Object> map);
    void insertIntoHref(Map<String,Object> map);
    void deleteWenhuImageById(Integer id);
    void deleteHrefById(Integer id);
    void updateArticle(Map<String,Object> map);
    void deleteBokeById(Integer id);
    List<WenhuaImage> selectAllImages();
    List<Href> selectAllHrefs();
    void insertIntoUser(Map<String,Object> map);
    void updateUser(Map<String,Object> map);
    void deleteUserById(Integer id);
}
