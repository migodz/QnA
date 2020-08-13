package top.migod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.migod.mapper.QuestionMapper;
import top.migod.mapper.UserMapper;
import top.migod.pojo.Question;
import top.migod.pojo.User;
import top.migod.service.QuestionService;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class PanelController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    //问答版页面跳转
    @RequestMapping(value = {"/u/{username}"})
    public String panel_redirect(@PathVariable(name = "username") String username) {
        return "redirect:/u/" + username + ".html";
    }

    //访问问答版页面
    @RequestMapping(value = {"/u/{username}.html"})
    public String panel(@PathVariable(name = "username") String username,
                        @RequestParam(name = "p", required = false) Integer p,
                        HttpSession session,
                        Model model) {
        User viewUser = userMapper.selectByUsername(username);

        if (viewUser == null) {
            return "/error/404";
        }

        model.addAttribute("view_user", viewUser); //存入版主用户
        model.addAttribute("page_num", questionService.getPageNumByUid(viewUser.getUid())); //存入最大页数

        //存入当前页数和问题数据
        if (p==null) {
            model.addAttribute("page",1);
            model.addAttribute("questions", questionService.selectByUidAndPage(viewUser.getUid(), 1));
        }
        else {
            model.addAttribute("page",p);
            model.addAttribute("questions", questionService.selectByUidAndPage(viewUser.getUid(), p));
        }

        return "panel";
    }
}
