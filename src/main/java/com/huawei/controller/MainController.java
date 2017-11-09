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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String register(String username, String password, Model model) {
        System.out.println(username + "---" + password);
        //用于页面回显时存储数据的对象
        model.addAttribute("username", username);
        return "user/register";
    }

    /**
     * 处理登录请求的登录
     * 返回json数据
     *
     * @ResponseBody 声明当前请求返回的数据为json数据
     */
    @ResponseBody
    @RequestMapping("/loginu")
    public AjaxLoginResult loginu(User user,Model model) {
        /**
         * 发起业务查询
         */
        AjaxLoginResult result = new AjaxLoginResult();
        if (user != null && user.getUsername() != null) {
            User user1 = userService.findByName(user.getUsername());
            if (user1 != null) {
                model.addAttribute("user",user1);
                result.setErrorCode(0);
                result.setData(user1);
            } else {
                result.setErrorCode(606);
                result.setMessage("用户不存在" + user.getUsername());
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
    public String blogpage(@PathVariable Integer userId, Model model,
                           HttpServletRequest request, HttpServletResponse response) {

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

        System.out.println(id);
        Blog blog = blogService.findSingle(id);
        model.addAttribute("blog", blog);
        return "blog/blogdetail";
    }

    @RequestMapping("/addblog")
    public String addblog() {
        return "blog/addblog";
    }

    @RequestMapping("/newblog")
    public AjaxLoginResult newblog(Blog blog) {
        AjaxLoginResult result = new AjaxLoginResult();
        int count = blogService.addBlog(blog);
        if (count > 0){
            result.setErrorCode(0);
            result.setMessage("添加博客成功");
        }else {
            result.setErrorCode(606);
            result.setMessage("添加博客失败");
        }
        return result;
    }



    @RequestMapping("/deleteblog")
    public String deleteblog(){

        return "";
    }

}
