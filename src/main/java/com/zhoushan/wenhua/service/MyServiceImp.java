package com.zhoushan.wenhua.service;

import com.zhoushan.wenhua.mapper.WenhuaMapper;
import com.zhoushan.wenhua.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyServiceImp implements MyService {

    @Autowired
    private WenhuaMapper wenhuaMapper;

    @Override
    public Wenhua selectWenhuaById(Integer id) {
        return wenhuaMapper.selectWenhuaById(id);
    }

    @Override
    public List<WenhuaImage> selectImagesById(Integer id) {
        return wenhuaMapper.selectImagesById(id);
    }

    @Override
    public List<Href> selectHrefsById(Integer id) {
        return wenhuaMapper.selectHrefsById(id);
    }

    @Override
    public List<Article> selectAllArticles() {
        return wenhuaMapper.selectAllArticles();
    }

    @Override
    public User selectUserByName(String username) {
        return wenhuaMapper.selectUserByName(username);
    }

    @Override
    public void insertIntoArticle(Map<String, Object> map) {
        wenhuaMapper.insertIntoArticle(map);
    }

    @Override
    public Article selectArticleById(int id) {
        return wenhuaMapper.selectArticleById(id);
    }

    @Override
    public void updataCommentsById(String str) {
        wenhuaMapper.updataCommentsById(str);
    }

    @Override
    public void updateWenhuaById(int id) {
        wenhuaMapper.updateWenhuaById(id);
    }

    @Override
    public List<Wenhua> selectWenhuaDesc() {
        return wenhuaMapper.selectWenhuaDesc();
    }

    @Override
    public List<Article> selectArticlesByType(String type) {
        return wenhuaMapper.selectArticlesByType(type);
    }

    @Override
    public List<Comment> selectCommentsById(int id) {
        return wenhuaMapper.selectCommentsById(id);
    }

    @Override
    public void updateCountById(int id) {
        wenhuaMapper.updateCountById(id);
    }

    @Override
    public User selectUserById(int id) {
        return wenhuaMapper.selectUserById(id);
    }

    @Override
    public void insertIntoComment(Map<String, Object> map) {
        wenhuaMapper.insertIntoComment(map);
    }

    @Override
    public List<Wenhua> selectWenhuaByTag(Integer id) {
        return wenhuaMapper.selectWenhuaByTag(id);
    }

    @Override
    public List<User> selectAllUsers() {
        return wenhuaMapper.selectAllUsers();
    }

    @Override
    public void updateWenhua(Map<String, Object> map) {
        wenhuaMapper.updateWenhua(map);
    }

    @Override
    public void insertIntoWenhua(Map<String, Object> map) {
        wenhuaMapper.insertIntoWenhua(map);
    }

    @Override
    public void deleteSpecialById(int id) {
        wenhuaMapper.deleteSpecialById(id);
    }

    @Override
    public void insertIntowenhuImage(Map<String, Object> map) {
        wenhuaMapper.insertIntowenhuImage(map);
    }

    @Override
    public void insertIntoHref(Map<String, Object> map) {
        wenhuaMapper.insertIntoHref(map);
    }

    @Override
    public void deleteWenhuImageById(Integer id) {
        wenhuaMapper.deleteWenhuImageById(id);
    }

    @Override
    public void deleteHrefById(Integer id) {
        wenhuaMapper.deleteHrefById(id);

    }

    @Override
    public void updateArticle(Map<String, Object> map) {
        wenhuaMapper.updateArticle(map);
    }

    @Override
    public void deleteBokeById(Integer id) {
        wenhuaMapper.deleteBokeById(id);
    }

    @Override
    public List<WenhuaImage> selectAllImages() {
        return wenhuaMapper.selectAllImages();
    }

    @Override
    public List<Href> selectAllHrefs() {
        return wenhuaMapper.selectAllHrefs();
    }

    @Override
    public void insertIntoUser(Map<String, Object> map) {
        wenhuaMapper.insertIntoUser(map);
    }

    @Override
    public void updateUser(Map<String, Object> map) {
        wenhuaMapper.updateUser(map);
    }

    @Override
    public void deleteUserById(Integer id) {
        wenhuaMapper.deleteUserById(id);
    }
}
