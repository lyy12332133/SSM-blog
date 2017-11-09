package com.huawei.controller;

import com.huawei.domain.Blog;
import com.huawei.domain.User;
import com.huawei.response.AjaxLoginResult;
import com.huawei.service.BlogService;
import com.huawei.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 17/11/8.
 */

/**
 * @Controller 代表当前这个类是一个控制器, 类似于Struts2的Action
 * 当浏览器发出请求时HandlerMapper会进行匹配访问路径是否在这个类中定义了
 */
@Controller
public class MainController {

    /**
     * 持有的服务层对象
     */
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @Autowired
    @Qualifier("blogService")
    private BlogService blogService;

    /**
     * @RequestMapping 定义能处理的请求路径
     * 例如: localhost:8080/home
     * 返回的视图, 根据spring配置文件中的视图解析定义自动加上前缀和后缀
     * 即方法/home时, 跳转的页面为
     * localhost:8080/WEB-INF/pages/home.jsp
     */
    @RequestMapping("/home")
    public String home() {
        System.out.println("访问了home");
        return "home";
    }

    /**
     * 处理index的访问请求
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 登录响应
     * 处理login的访问请求
     */
    @RequestMapping("/login")
    public String login() {
        return "user/login";
    }

    /**
     * 注册响应
     * 请求路径: /register?username='张三'&password='123'
     * 返回视图: /WEB-INF/pages/user/register.jsp
     */
    @RequestMapping("/register")
    public String register() {
        return "user/register";
    }

    @RequestMapping("/registerUser")
    public String registerUser(User user){
        int count = userService.register(user);
        return "user/login";
    }

    /**
     * 处理登录请求的登录
     * 返回json数据
     *
     * @ResponseBody 声明当前请求返回的数据为json数据
     */
    @ResponseBody
    @RequestMapping("/loginu")
    public AjaxLoginResult loginu(User user) {
        /**
         * 发起业务查询
         */
        AjaxLoginResult result = new AjaxLoginResult();
        if (user != null && user.getUsername() != null) {
            User user1 = userService.findByName(user.getUsername());
            if (user1 == null) {
                result.setErrorCode(606);
                result.setMessage("用户不存在");
            } else {
                User user2 = userService.findByUser(user);
                if (user2 == null){
                    result.setErrorCode(606);
                    result.setMessage("您输入的密码有误");
                }else {
                    result.setErrorCode(0);
                    result.setMessage("登录成功");
                    result.setData(user2);
                }
            }
        }
        return result;
    }


    /**
     * 请求进入博客列表界面
     *
     * @PathVariable 获取路径中同名的对象值
     */
    @RequestMapping("/blogpage/{userId}")
    public String blogpage(@PathVariable Integer userId, Model model) {

        List<Blog> blogList = blogService.findByUid(userId);

        //将blogList存储到model对象中
        model.addAttribute("blogList", blogList);
        return "blog/blogpage";
    }


    /**
     * 根据点击不同的博客进入不同博客详情页面
     *
     * @param id    博客id
     * @param model 驱动
     * @return
     */
    @RequestMapping("/blogdetail/{id}")
    public String blogdetail(@PathVariable Integer id, Model model) {

        Blog blog = blogService.findSingle(id);
        model.addAttribute("blog", blog);
        return "blog/blogdetail";
    }

    @RequestMapping("/addblog")
    public String addblog() {
        return "blog/addblog";
    }

    /**
     * 添加博客
     * @ResponseBody 返回json数据注解
     */
    @ResponseBody
    @RequestMapping("/newblog")
    public AjaxLoginResult newblog(Blog blog) {
        AjaxLoginResult result = new AjaxLoginResult();
        int count = blogService.addBlog(blog);
        if (count > 0) {
            result.setErrorCode(0);
            result.setMessage("添加博客成功");
        } else {
            result.setErrorCode(606);
            result.setMessage("添加博客失败");
        }
        return result;
    }


    /**
     * 删除博客
     * @param blog
     */
    @ResponseBody
    @RequestMapping("/deleteblog")
    public AjaxLoginResult deleteblog(Blog blog) {
        AjaxLoginResult result = new AjaxLoginResult();
        int count =  blogService.deleteBlog(blog);
        if (count > 0){
            result.setErrorCode(0);
            result.setMessage("删除成功");
        }else {
            result.setErrorCode(606);
            result.setMessage("删除失败");
        }
        return result;
    }


    /**
     * 高级查询
     * @param condition 条件
     * @param model 驱动
     * @return
     */
    @RequestMapping("/fuzzyQuery")
    public String fuzzyQuery(Blog blog, String condition, Model model){
        blog.setTitle(condition);
        blog.setDes(condition);
        blog.setContent(condition);
        List<Blog> blogList1 =  blogService.findByTitle(blog);
        List<Blog> blogList2 =  blogService.findByDes(blog);
        List<Blog> blogList3 =  blogService.findByContent(blog);
        blogList1.removeAll(blogList2);
        blogList1.removeAll(blogList3);
        blogList2.removeAll(blogList1);
        blogList2.removeAll(blogList3);
        blogList3.removeAll(blogList1);
        blogList3.removeAll(blogList2);
        List<Blog> blogList = new ArrayList<Blog>();
        blogList.addAll(blogList1);
        blogList.addAll(blogList2);
        blogList.addAll(blogList3);
        model.addAttribute("blogList", blogList);
        return "blog/blogpage";
    }

}
