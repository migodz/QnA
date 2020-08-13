package top.migod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import top.migod.mapper.UserMapper;
import top.migod.pojo.User;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserMapper userMapper;

    //登录页面跳转
    @RequestMapping({"/","/login", "/signin", "/signin.html"})
    public String login_redirect() {
        return "redirect:/login.html";
    }

    //访问登录页面
    @RequestMapping("/login.html")
    public String login(HttpSession session) {
        User currentUser = (User) session.getAttribute("current_user");
        if (currentUser != null)
            return "redirect:/u/" + currentUser.getUsername() + ".html";

        return "signin";
    }

    //登录表单验证
    @RequestMapping("/login_submit")
    public String login_submit(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               HttpSession session,
                               Model model) {
        User loginUser = userMapper.selectByUsername(username);
        if (loginUser == null || !password.equals(loginUser.getPassword())) {
            model.addAttribute("msg", "用户名或密码错误！");
            return "signin";
        }

        session.setAttribute("current_user", loginUser);
        return "redirect:/u/" + username +".html";
    }

    //退出登录
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("current_user");
        return "redirect:/login.html";
    }
}
