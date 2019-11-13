package com.zhoushan.wenhua.myController;


import com.zhoushan.wenhua.model.*;
import com.zhoushan.wenhua.service.MyService;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.session.HttpServletSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TestController {

    @Autowired
    private MyService myService;

    @Value("${mypath}")
    private String path;

    @RequestMapping("/index")
    public String caozuo(Model model){


        System.out.println(path);


        List<Wenhua> special = myService.selectWenhuaByTag(1);
        List<Wenhua> not = myService.selectWenhuaByTag(2);
        model.addAttribute("special",special);
        model.addAttribute("specialcount",special.size());
        model.addAttribute("noTheredity",not);
        System.out.println(not.size());
        model.addAttribute("notcount",not.size());

        List<Article> articles = myService.selectAllArticles();
        Collections.reverse(articles);
        List<Article> lists = articles.subList(0,3);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for(int i = 0 ; i < lists.size(); i++){
            long ltime2 = Long.parseLong(lists.get(i).getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            lists.get(i).setTime(lStrDate);

        }
        if(SecurityUtils.getSubject().getPrincipal() != null){
            User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());
            model.addAttribute("logo",user.getLogopath());
        }


        model.addAttribute("articles",lists);

        return "index";
    }

    @RequestMapping("/add")
    public String fresh(HttpServletRequest request, Model model,int cur)throws Exception{
        HttpSession session = request.getSession();
//        int cur = Integer.parseInt(session.getAttribute("cur").toString());
        List<Article> articles = new ArrayList<>();
        if(request.getParameter("kind") != null){
            articles = myService.selectArticlesByType(request.getParameter("kind"));
        }else{
            articles = myService.selectAllArticles();
        }

        Collections.reverse(articles);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<User> lists = new ArrayList<>();

        for(int i = 0 ; i < articles.size(); i++){
            long ltime2 = Long.parseLong(articles.get(i).getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            articles.get(i).setTime(lStrDate);
            lists.add(myService.selectUserByName(articles.get(i).getAuthName()));

        }

        model.addAttribute("all",session.getAttribute("all"));

            model.addAttribute("cur",cur+1);
            if((cur+1)*10>articles.size()){
                model.addAttribute("articles",articles.subList(cur*10,articles.size()));
                model.addAttribute("users",lists.subList(cur*10,articles.size()));

            }else{
                model.addAttribute("articles",articles.subList(cur*10,(cur+1)*10));
                model.addAttribute("users",lists.subList(cur*10,(cur+1)*10));
            }

        return "boke::table_refresh";

    }
    @RequestMapping("/decrease")
    public String decrease(HttpServletRequest request, Model model,int cur)throws Exception{
        HttpSession session = request.getSession();
//        String how = request.getParameter("add");
        List<User> lists = new ArrayList<>();
        List<Article> articles = new ArrayList<>();
        if(request.getParameter("kind") != null){
            articles = myService.selectArticlesByType(request.getParameter("kind"));
        }else{
            articles = myService.selectAllArticles();
        }

        Collections.reverse(articles);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



        for(int i = 0 ; i < articles.size(); i++){
            long ltime2 = Long.parseLong(articles.get(i).getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            articles.get(i).setTime(lStrDate);
            lists.add(myService.selectUserByName(articles.get(i).getAuthName()));

        }

        model.addAttribute("all",session.getAttribute("all"));


            model.addAttribute("cur",cur-1);
            model.addAttribute("articles",articles.subList((cur - 2)*10,(cur -1)*10));
            model.addAttribute("users",lists.subList((cur - 2)*10,(cur -1)*10));

        return "boke::table_refresh";

    }
    @RequestMapping("/random")
    public String random(HttpServletRequest request, Model model)throws Exception{
System.out.println("进来了");
        List<Article> lists = myService.selectAllArticles();
        int rand = lists.size();
        List<Article> list = new ArrayList<>();
        Random random = new Random();
        int count = 0;
        while(count < 3){
         int cur = random.nextInt(rand);
         if(list.contains(lists.get(cur))){
             continue;
         }else{
             list.add(lists.get(cur));
             count++;
         }

       }

        for(int i = 0 ; i < list.size(); i++){
            long ltime2 = Long.parseLong(list.get(i).getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            list.get(i).setTime(lStrDate);

        }


        model.addAttribute("articles",list);

        return "index::table_refresh";

    }
    @RequestMapping("/luntan/{id}/{cur}")
    public String luntan(@PathVariable(value = "id",required = true)int id,@PathVariable(value = "cur",required = true)int cur,Model model){

        Article article = myService.selectArticleById(id);
        myService.updateCountById(id);
        List<Comment> comments = myService.selectCommentsById(id);
//        Collections.reverse(comments);
        User author = myService.selectUserById(article.getIswhose());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            long ltime2 = Long.parseLong(article.getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            article.setTime(lStrDate);

            List<User> users = new ArrayList<>();
            for(Comment comment:comments){
                 ltime2 = Long.parseLong(comment.getTime());
                 lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                 lDate = new Date(ltime2);
                 lStrDate = lsdFormat.format(lDate);
                comment.setTime(lStrDate);

                users.add(myService.selectUserById(comment.getIswhose()));
            }
            int all = 0;
            if(comments.size() % 10 != 0){
                all = comments.size() / 10 +1;
            }else{
                all = comments.size() /10;
            }

            if(all == 0){
                all = 1;
            }
            model.addAttribute("cur",cur);
            model.addAttribute("all",all);
            model.addAttribute("author",author);
            model.addAttribute("article",article);
            model.addAttribute("users",users);
            model.addAttribute("articleId",article.getId());
            model.addAttribute("comments",comments.subList((cur-1)*10,cur*10>comments.size()?comments.size():cur*10 - 1));




        return "luntan";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/huifu")
    public void huifu(HttpServletRequest request,HttpServletResponse response)throws Exception{
        User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());

        Comment comment = new Comment();
        comment.setContent(request.getParameter("html"));
        comment.setIswhose(user.getId());
        comment.setTowhose(Integer.parseInt(request.getParameter("id")));
        myService.updataCommentsById(request.getParameter("id"));
        comment.setTime(System.currentTimeMillis()+"");
        Map<String,Object> map = new HashMap<>();
        map.put("comment",comment);
        response.setCharacterEncoding("UTF-8");


        Map<String,Object> info = new HashMap<>();

        try{
            myService.insertIntoComment(map);
            info.put("success",true);
            info.put("id",request.getParameter("id"));
        }catch (Exception e){
            info.put("success",false);
            info.put("msg","提交失败");
        }finally{
            JSONObject jsonObject = JSONObject.fromObject(info);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(jsonObject);
        }


    }

    @RequestMapping("/boke")
    public String boke(Model model, HttpServletRequest request){


        List<Wenhua> special = myService.selectWenhuaByTag(1);
        List<Wenhua> not = myService.selectWenhuaByTag(2);
        model.addAttribute("special",special);
        model.addAttribute("specialcount",special.size());
        model.addAttribute("noTheredity",not);
        System.out.println(not.size());
        model.addAttribute("notcount",not.size());

     List<Article> articles = myService.selectAllArticles();
        Collections.reverse(articles);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        model.addAttribute("cur",1);
        int all = 0;
        if(articles.size() % 10 == 0){
            all = articles.size() / 10;
        }else{
            all = articles.size() / 10 + 1;
        }

        model.addAttribute("all",all);

        List<User> users = new ArrayList<>();
        for(int i = 0 ; i < articles.size(); i++){
            long ltime2 = Long.parseLong(articles.get(i).getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            articles.get(i).setTime(lStrDate);
            users.add(myService.selectUserById(articles.get(i).getIswhose()));
        }


        HttpSession session = request.getSession();
        session.removeAttribute("all");
        session.setAttribute("all",all);
        System.out.println(all+ "有问题吧");

        model.addAttribute("articles",articles.subList(0,(10 >= articles.size())?articles.size():10));
        model.addAttribute("users",users.subList(0,(10 >= articles.size())?articles.size():10));
       if(SecurityUtils.getSubject().getPrincipal() != null){
           String userName = ((User)SecurityUtils.getSubject().getPrincipal()).getUsername();
           User user = myService.selectUserByName(userName);

           model.addAttribute("username",user.getUsername());
           model.addAttribute("gxqm",user.getGxqm());
       }

        if(SecurityUtils.getSubject().getPrincipal() != null){
            User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());
            model.addAttribute("logo",user.getLogopath());
        }


        return "boke";
    }
    @RequestMapping("/kind")
    public String search(Model model, HttpServletRequest request,String type){

        List<Wenhua> special = myService.selectWenhuaByTag(1);
        List<Wenhua> not = myService.selectWenhuaByTag(2);
        model.addAttribute("special",special);
        model.addAttribute("specialcount",special.size());
        model.addAttribute("noTheredity",not);
        System.out.println(not.size());
        model.addAttribute("notcount",not.size());

        model.addAttribute("kind",type);

     List<Article> articles = myService.selectArticlesByType(type);
        Collections.reverse(articles);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        HttpSession session =  request.getSession();
        model.addAttribute("cur",1);
        int all = 0;
        if(articles.size() % 10 == 0){
            all = articles.size() / 10;
        }else{
            all = articles.size() / 10 + 1;
        }
        if(all == 0){
            all = 1;
        }
        model.addAttribute("all",all);

        session.setAttribute("cur",1);
        session.setAttribute("all",all);
        List<User> users = new ArrayList<>();
        for(int i = 0 ; i < articles.size(); i++){
            long ltime2 = Long.parseLong(articles.get(i).getTime());
            SimpleDateFormat lsdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date lDate = new Date(ltime2);
            String lStrDate = lsdFormat.format(lDate);
            articles.get(i).setTime(lStrDate);
            users.add(myService.selectUserById(articles.get(i).getIswhose()));
        }



        model.addAttribute("articles",articles.subList(0,(10 >= articles.size())?articles.size():10));
        model.addAttribute("users",users.subList(0,(10 >= articles.size())?articles.size():10));
       if(SecurityUtils.getSubject().getPrincipal() != null){
           String userName = ((User)SecurityUtils.getSubject().getPrincipal()).getUsername();
           User user = myService.selectUserByName(userName);

           model.addAttribute("username",user.getUsername());
           model.addAttribute("gxqm",user.getGxqm());
       }

        if(SecurityUtils.getSubject().getPrincipal() != null){
            User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());
            model.addAttribute("logo",user.getLogopath());
        }


        return "boke";
    }
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable(value = "id",required = true)int id, Model model)
    {
        List<Wenhua> special = myService.selectWenhuaByTag(1);
        List<Wenhua> not = myService.selectWenhuaByTag(2);
        model.addAttribute("special",special);
        model.addAttribute("specialcount",special.size());
        model.addAttribute("noTheredity",not);
        System.out.println(not.size());
        model.addAttribute("notcount",not.size());

        List<Wenhua> list = myService.selectWenhuaDesc();
        model.addAttribute("wenhua",list.subList(0,4));

        Wenhua wenhua = myService.selectWenhuaById(id);
        List<WenhuaImage> images = myService.selectImagesById(wenhua.getId());
        model.addAttribute("desc",wenhua.getDesc());
        model.addAttribute("title",wenhua.getName());
        model.addAttribute("images",images);
        model.addAttribute("logopath",wenhua.getLogopath());
        model.addAttribute("video",wenhua.getVideo());
        model.addAttribute("count",wenhua.getCount());
        model.addAttribute("hrefs",myService.selectHrefsById(id));

        myService.updateWenhuaById(id);
        if(SecurityUtils.getSubject().getPrincipal() != null){
            User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());
            model.addAttribute("logo",user.getLogopath());
        }
        return "detail";
    }
    @RequestMapping("/sp")
    public String sp(){
        return "sp";
    }
    @RequestMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "hello";
    }

    @RequestMapping("/fatie")
    public String fatie(Model model){

        List<Wenhua> list1 = myService.selectWenhuaByTag(1);
        List<Wenhua> list2 = myService.selectWenhuaByTag(2);

       List<Wenhua> list3 = new ArrayList<>();
       list3.addAll(list1);
       list3.addAll(list2);

       model.addAttribute("lists",list3);

        return "fatie";
    }

    @RequestMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        System.out.println("ddd");
        return "forward:/index";
    }

    @RequestMapping("/check")
    public String check(HttpServletRequest request,Model model){
        Subject subject = SecurityUtils.getSubject();
        String username = request.getParameter("username");
        String password = request.getParameter("pwd");
        System.out.println(username);
        System.out.println(password);
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        //3.执行登录方法
        try {
            subject.login(token);

        }catch (UnknownAccountException e1){
            model.addAttribute("msg","用户名不存在");
            return "forward:/login";
        }catch(IncorrectCredentialsException e2){
            model.addAttribute("msg","密码错误");
            return "forward:/login";
        }
        User uu = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());
        System.out.println(uu.getPerms());
        if(uu.getPerms().equals("user:all")){
            return "houtai";
        }
        return "forward:/index";
    }

    @RequestMapping("/release")
    public void release(HttpServletRequest request,HttpServletResponse response)throws Exception{

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String kind = request.getParameter("kind");

        User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());

        Article article = new Article();
        article.setTime(System.currentTimeMillis()+"");
        article.setAuthName(user.getUsername());
        article.setComments(0);
        article.setContent(content);
        article.setCount(0);
        article.setGood(0);
        article.setIswhose(user.getId());
        article.setKind(kind);
        article.setTitle(title);
        Map<String,Object> map = new HashMap<>();
        map.put("article",article);
        response.setCharacterEncoding("utf-8");

        try{
            myService.insertIntoArticle(map);
            response.getWriter().println("发布成功");
        }catch (Exception e){
            response.getWriter().println("发布失败");
        }
    }

    @RequestMapping("/special")
    public void special(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<Wenhua> wenhua = myService.selectWenhuaByTag(1);
        int all = wenhua.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<Wenhua> lists = wenhua.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.getWriter().println(result);


    }
    @RequestMapping("/tospecial")
    public String tospecial(Model model){


        return "special";

    }
    @RequestMapping("/tongji")
    public String tongji(Model model){


        return "tongji";

    }
    @RequestMapping("/not")
    public void not(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<Wenhua> wenhua = myService.selectWenhuaByTag(2);
        int all = wenhua.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<Wenhua> lists = wenhua.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.getWriter().println(result);


    }
    @RequestMapping("/paixu")
    public void paixu(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<Wenhua> wenhua = myService.selectWenhuaDesc();
        int all = wenhua.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<Wenhua> lists = wenhua.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.getWriter().println(result);


    }
    @RequestMapping("/tonot")
    public String tonot(Model model){


        return "not";

    }
    @RequestMapping("/seeboke")
    public void seeboke(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<Article> wenhua = myService.selectAllArticles();
        int all = wenhua.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<Article> lists = wenhua.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.getWriter().println(result);


    }
    @RequestMapping("/getHrefs")
    public void getHrefs(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<Href> wenhua = myService.selectAllHrefs();
        int all = wenhua.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<Href> lists = wenhua.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(result);


    }
    @RequestMapping("/toboke")
    public String toboke(Model model){


        return "seeboke";

    }
    @RequestMapping("/user")
    public void user(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<User> wenhua = myService.selectAllUsers();
        int all = wenhua.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<User> lists = wenhua.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.getWriter().println(result);


    }
    @RequestMapping("/getImages")
    public void getImages(Model model,int page,int rows,HttpServletResponse response)throws Exception{


        JSONObject result = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        List<WenhuaImage> images = myService.selectAllImages();
        int all = images.size();
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        int start = (page - 1 ) * 10;
        int end = (page * 10 > all )?all : page * 10;
        List<WenhuaImage> lists = images.subList(start, end);
        jsonMap.put("total", all);
        jsonMap.put("rows",lists );
        result = JSONObject.fromObject(jsonMap);
        response.getWriter().println(result);


    }
    @RequestMapping("/tousers")
    public String tousers(Model model){


        return "user";

    }
    @RequestMapping("/other")
    public String other(Model model){

        List<Wenhua> special = myService.selectWenhuaByTag(1);
        List<Wenhua> not = myService.selectWenhuaByTag(2);
        model.addAttribute("special",special);
        model.addAttribute("specialcount",special.size());
        model.addAttribute("noTheredity",not);
        System.out.println(not.size());
        model.addAttribute("notcount",not.size());


        if(SecurityUtils.getSubject().getPrincipal() != null){
            User user = myService.selectUserByName(((User)SecurityUtils.getSubject().getPrincipal()).getUsername());
            model.addAttribute("logo",user.getLogopath());
        }
     List<Href> lists = myService.selectAllHrefs();
     model.addAttribute("lists",lists);
        return "other";

    }
}
