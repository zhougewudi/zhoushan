package com.zhoushan.wenhua.myController;

import com.zhoushan.wenhua.model.*;
import com.zhoushan.wenhua.service.MyService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class HoutaiController {


    @Autowired
    private MyService myService;

    @RequestMapping("/getspecial")
    public void getspecial(int id, HttpServletResponse response)throws Exception{
        JSONObject result = new JSONObject();
        System.out.println(id);
        Wenhua wenhua = myService.selectWenhuaById(id);


        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("id",id);
        jsonMap.put("name", wenhua.getName());
        jsonMap.put("desc", wenhua.getDesc());
        jsonMap.put("count", wenhua.getCount());
        jsonMap.put("video", wenhua.getVideo());
        jsonMap.put("logopath", wenhua.getLogopath());
        jsonMap.put("tag", wenhua.getTag());
        result = JSONObject.fromObject(jsonMap);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(result);
    }
    @RequestMapping("/getuser")
    public void getuser(int id, HttpServletResponse response)throws Exception{
        JSONObject result = new JSONObject();
        System.out.println(id);
        User user = myService.selectUserById(id);


        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("id",id);
        jsonMap.put("username",user.getUsername());
        jsonMap.put("logopath",user.getLogopath());
        jsonMap.put("perms",user.getPerms());
        jsonMap.put("gxqm",user.getGxqm());
        jsonMap.put("likelist",user.getLikelist());
        jsonMap.put("password",user.getPassword());

        result = JSONObject.fromObject(jsonMap);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(result);
    }


    @RequestMapping("/getboke")
    public void getboke(int id, HttpServletResponse response)throws Exception{
        JSONObject result = new JSONObject();

        Article article = myService.selectArticleById(id);

        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put("id",id);
        jsonMap.put("kind", article.getKind());
        jsonMap.put("count", article.getCount());
        jsonMap.put("content", article.getContent());
        jsonMap.put("good", article.getGood());
        jsonMap.put("iswhose", article.getIswhose());
        jsonMap.put("title", article.getTitle());
        jsonMap.put("time", article.getTime());
        jsonMap.put("comments", article.getComments());
        jsonMap.put("authName", article.getAuthName());
        result = JSONObject.fromObject(jsonMap);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(result);
    }

    @RequestMapping("/savespecial")
    public void savespecial(HttpServletRequest request, String name, String logopath, String desc, int count, String video, int tag, HttpServletResponse response)throws Exception{
        String id = request.getParameter("id");
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        try{  if(id == null || id.equals("")){
            System.out.println("哈哈哈");
               Wenhua cur = new Wenhua();

               cur.setCount(count);
               cur.setDesc(desc);
               cur.setVideo(video);
               cur.setTag(tag);
               cur.setName(name);
               cur.setLogopath(logopath);
               Map<String,Object> map = new HashMap<>();
               map.put("wenhua",cur);
               myService.insertIntoWenhua(map);
               System.out.println("执行插入");
           }else{
               Wenhua cur = new Wenhua();
               cur.setCount(count);
               cur.setDesc(desc);
               cur.setVideo(video);
               cur.setTag(tag);
               cur.setName(name);
               cur.setLogopath(logopath);
               cur.setId(Integer.parseInt(id));
               Map<String,Object> map = new HashMap<>();
               map.put("wenhua",cur);
               myService.updateWenhua(map);
               System.out.println("执行更新");
           }
           jsonMap.put("success",true);
       }catch (Exception e){
           System.out.println("失败了");System.out.println(e.getMessage());
           jsonMap.put("success",false);

       }finally {
           JSONObject result = new JSONObject();
           result = JSONObject.fromObject(jsonMap);
           response.setCharacterEncoding("UTF-8");
           response.getWriter().println(result);
       }



    }
    @RequestMapping("/saveuser")
    public void saveuser(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String id = request.getParameter("id");
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        try{  if(id == null || id.equals("")){
            User user = new User();
            user.setGxqm(request.getParameter("gxqm"));
            user.setLogopath(request.getParameter("logopath"));
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setPerms(request.getParameter("perms"));
            Map<String,Object> map = new HashMap<>();
            map.put("user",user);
            myService.insertIntoUser(map);
               System.out.println("执行插入");
           }else{
            User user = new User();
            user.setGxqm(request.getParameter("gxqm"));
            user.setLogopath(request.getParameter("logopath"));
            user.setUsername(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            user.setPerms(request.getParameter("perms"));
            user.setId(Integer.parseInt(id));
               Map<String,Object> map = new HashMap<>();
               map.put("user",user);
               myService.updateUser(map);
               System.out.println("执行更新");
           }
           jsonMap.put("success",true);
       }catch (Exception e){
           System.out.println("失败了");System.out.println(e.getMessage());
           jsonMap.put("success",false);

       }finally {
           JSONObject result = new JSONObject();
           result = JSONObject.fromObject(jsonMap);
           response.setCharacterEncoding("UTF-8");
           response.getWriter().println(result);
       }



    }
    @RequestMapping("/saveboke")
    public void saveboke(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String id = request.getParameter("id");
        Map<String, Object> jsonMap = new HashMap<String, Object>();

        try{  if(id == null || id.equals("")){
            Article article = new Article();

            User user = myService.selectUserByName(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());
            article.setTitle(request.getParameter("title"));
            article.setCount(0);
            article.setKind(request.getParameter("kind"));
            article.setContent(request.getParameter("content"));
            article.setGood(0);
            article.setIswhose(user.getId());
            article.setAuthName(user.getUsername());
            article.setComments(0);
            article.setTime(System.currentTimeMillis() + "");


               Map<String,Object> map = new HashMap<>();
               map.put("article",article);
               myService.insertIntoArticle(map);
               System.out.println("执行插入");
           }else{
               Article article = new Article();

               article.setTime(request.getParameter("time"));
               article.setComments(Integer.parseInt(request.getParameter("comments")));
               article.setAuthName(request.getParameter("authName"));
               article.setIswhose(Integer.parseInt(request.getParameter("iswhose")));
               article.setGood(Integer.parseInt(request.getParameter("good")));
               article.setContent(request.getParameter("content"));
               article.setKind(request.getParameter("kind"));
               article.setTitle(request.getParameter("title"));
               article.setId(Integer.parseInt(id));
               article.setCount(Integer.parseInt(request.getParameter("count")));

               Map<String,Object> map = new HashMap<>();
               map.put("article",article);
               myService.updateArticle(map);
               System.out.println("执行更新");
           }
           jsonMap.put("success",true);
       }catch (Exception e){
           System.out.println("失败了");System.out.println(e.getMessage());
           jsonMap.put("success",false);

       }finally {
           JSONObject result = new JSONObject();
           result = JSONObject.fromObject(jsonMap);
           response.setCharacterEncoding("UTF-8");
           response.getWriter().println(result);
       }



    }

    @RequestMapping("/deletespecial")
    public void deletespecial(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String ids = request.getParameter("ids");
        System.out.println(ids);
        try{
            String[] id = ids.split(",");

            for(String str: id){
                myService.deleteSpecialById(Integer.parseInt(str));
                List<WenhuaImage> lists = myService.selectImagesById(Integer.parseInt(str));
                System.out.println("hello" + lists.size());
                List<Href> list = myService.selectHrefsById(Integer.parseInt(str));
                System.out.println("hello" + list.size());
                for(int i = 0 ; i < lists.size() ; i++){
                    myService.deleteWenhuImageById(lists.get(i).getId());
                }
                for(int i = 0 ; i < list.size() ; i++){
                    myService.deleteHrefById(list.get(i).getId());
                }
            }
            jsonMap.put("success",true);
        }catch (Exception e){
            jsonMap.put("success",false);
        }finally {
            JSONObject result = new JSONObject();
            result = JSONObject.fromObject(jsonMap);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);
        }


    }
    @RequestMapping("/deleteboke")
    public void deleteboke(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String ids = request.getParameter("ids");
        System.out.println(ids);
        try{
            String[] id = ids.split(",");

            for(String str: id){
                myService.deleteBokeById(Integer.parseInt(str));
            }
            jsonMap.put("success",true);
        }catch (Exception e){
            jsonMap.put("success",false);
        }finally {
            JSONObject result = new JSONObject();
            result = JSONObject.fromObject(jsonMap);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);
        }


    }
    @RequestMapping("/deleteuser")
    public void deleteuser(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String ids = request.getParameter("ids");
        System.out.println(ids);
        try{
            String[] id = ids.split(",");

            for(String str: id){
                myService.deleteUserById(Integer.parseInt(str));
            }
            jsonMap.put("success",true);
        }catch (Exception e){
            jsonMap.put("success",false);
        }finally {
            JSONObject result = new JSONObject();
            result = JSONObject.fromObject(jsonMap);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);
        }


    }
    @RequestMapping("/deleteHref")
    public void deleteHref(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String ids = request.getParameter("ids");
        System.out.println(ids);
        try{
            String[] id = ids.split(",");

            for(String str: id){
                myService.deleteHrefById(Integer.parseInt(str));
            }
            jsonMap.put("success",true);
        }catch (Exception e){
            jsonMap.put("success",false);
        }finally {
            JSONObject result = new JSONObject();
            result = JSONObject.fromObject(jsonMap);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);
        }


    }
    @RequestMapping("/deleteImage")
    public void deleteImage(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        String ids = request.getParameter("ids");
        System.out.println(ids);
        try{
            String[] id = ids.split(",");

            for(String str: id){
                myService.deleteWenhuImageById(Integer.parseInt(str));
            }
            jsonMap.put("success",true);
        }catch (Exception e){
            jsonMap.put("success",false);
        }finally {
            JSONObject result = new JSONObject();
            result = JSONObject.fromObject(jsonMap);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(result);
        }


    }

    @RequestMapping("/toimage")
    public String toimage(){
        return "images";
    }
    @Transactional
    @RequestMapping("/upload")
    public void upload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest multiReq, Model model)throws Exception{
        Map<String, Object> map = new HashMap<>();
        Map<String,Object> info = new HashMap<>();
        response.setCharacterEncoding("UTF-8");
        // 获取上传文件的路径
        String uploadFilePath = multiReq.getFile("file1").getOriginalFilename();
        System.out.println("uploadFlePath:" + uploadFilePath);
        // 截取上传文件的文件名
        String uploadFileName = uploadFilePath.substring(
                uploadFilePath.lastIndexOf('\\') + 1, uploadFilePath.indexOf('.'));
        System.out.println("multiReq.getFile()" + uploadFileName);
        // 截取上传文件的后缀
        String uploadFileSuffix = uploadFilePath.substring(
                uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
        System.out.println("uploadFileSuffix:" + uploadFileSuffix);
        FileOutputStream fos = null;
        FileInputStream fis = null;
        WenhuaImage wenhuaImage = new WenhuaImage();

        try {
            fis = (FileInputStream) multiReq.getFile("file1").getInputStream();
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            System.currentTimeMillis();
            String  str = "" + year + month + day + System.currentTimeMillis();
            File file = new File("/usr/local/tomcat/webapps/start/WEB-INF/classes/static/images/");
//           File file = new File("src/main/resources/static/images/");
//            if (!file.exists()) {
//                file.mkdirs();
//                System.out.println("操作");
//                str = str + "\\" + uploadFileName + "." + uploadFileSuffix;
//                map.put("completedPath", str);
//            }
            System.out.println(request.getParameter("path") +"ddd");
            System.out.println(file.getAbsolutePath());
            wenhuaImage.setPath("/images/" + str + "." +uploadFileSuffix);
            wenhuaImage.setDesc(request.getParameter("desc"));
            wenhuaImage.setIswhose(Integer.parseInt(request.getParameter("iswhose")));

            Map<String,Object> mapb = new HashMap<>();
            mapb.put("wenhuaImage",wenhuaImage);
            myService.insertIntowenhuImage(mapb);
            fos = new FileOutputStream(new File(file.getAbsolutePath() +"/" + str
                    + ".")
                    + uploadFileSuffix);
            byte[] temp = new byte[1024];
            int i = fis.read(temp);
            while (i != -1) {
                fos.write(temp, 0, temp.length);
                fos.flush();
                i = fis.read(temp);
            }

            info.put("success",true);
            info.put("msg","资源上传成功");
        } catch (IOException e) {
            info.put("success",false);
            info.put("msg","消息发送失败");
            e.printStackTrace();
        } finally {

            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JSONObject jsonObject = JSONObject.fromObject(info);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(jsonObject);
        }
    }

    @RequestMapping("/tohref")
    public String tohref(){
        return "href";
    }
    @RequestMapping("/href")
    public void href(HttpServletRequest request,HttpServletResponse response)throws Exception{
        Href href = new Href();
        Map<String,Object> info = new HashMap<>();
        href.setIswhose(Integer.parseInt(request.getParameter("iswhose")));
        href.setDes(request.getParameter("des"));
        href.setContent(request.getParameter("content"));
        try{
            Map<String,Object> map = new HashMap<>();
            map.put("href",href);
            myService.insertIntoHref(map);
            info.put("success",true);
            info.put("msg","上传成功");
        }catch (Exception e){
            info.put("success",false);
            info.put("msg","上传失败");
        }finally {
            JSONObject jsonObject = JSONObject.fromObject(info);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(jsonObject);
        }
    }
}
