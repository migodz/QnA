package top.migod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import top.migod.mapper.TempInfoMapper;
import top.migod.mapper.UserMapper;
import top.migod.pojo.TempInfo;
import top.migod.pojo.User;
import top.migod.util.MailUtil;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class SignupController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    TempInfoMapper tempInfoMapper;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Autowired
    MailUtil mailUtil;

    @Value("${qna.domain}")
    String domain;

    //注册页面跳转
    @RequestMapping({"/signup", "/logup", "/logup.html"})
    public String signup_redirect() {
        return "redirect:/signup.html";
    }

    //访问注册页面
    @RequestMapping("/signup.html")
    public String signup() {
        return "signup";
    }

    //注册表单验证
    @PostMapping("/signup_submit")
    public String signup_submit(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("email") String email,
                                @RequestParam("nickname") String nickname,
                                Model model) throws UnsupportedEncodingException {
        if (userMapper.selectByUsername(username) != null) {
            model.addAttribute("msg", "该用户名已存在！");
            return "signup";
        }

        TempInfo tempInfo;
        //检查临时表中是否存在相同username的数据，如果有则直接修改原始数据
        if (tempInfoMapper.selectByUsername(username) != null) {
            tempInfo = tempInfoMapper.selectByUsername(username);
            tempInfo.setUsername(username);
            tempInfo.setEmail(email);
            tempInfo.setPassword(password);
            tempInfo.setNickname(nickname);
            tempInfoMapper.update(tempInfo);
        } else { //如果没有，储存用户数据到临时表，生成新激活码

            tempInfo = new TempInfo();
            tempInfo.setType(1); //用户激活临时数据
            tempInfo.setUsername(username);
            tempInfo.setEmail(email);
            tempInfo.setPassword(password);
            tempInfo.setNickname(nickname);

            //产生8位数字验证码
            String check_code = String.valueOf((int) (89999999 * Math.random() + 10000000));
            while (tempInfoMapper.selectByCode(check_code) != null) {
                check_code = String.valueOf((int) (89999999 * Math.random() + 10000000));
            }
            tempInfo.setCheck_code(check_code);
            tempInfoMapper.insert(tempInfo);
        }


        //发送激活邮件
        Context context = new Context();
        context.setVariable("title", "新用户激活");
        context.setVariable("content", "点击下方的超链接，即可激活您的QnA账户！如果您未申请注册QnA，请忽视这封邮件。（自动发送，请勿回复）");
        context.setVariable("url", "http://" + domain + "/activate/account/" + tempInfo.getCheck_code());
        context.setVariable("url_text", "http://" + domain + "/activate/account/" + tempInfo.getCheck_code());
        String mailContent = templateEngine.process("mail_notice", context);
        mailUtil.sendMail(email, "QnA账户激活 - QnA", mailContent);

        return "redirect:/login.html?msg_info=" + URLEncoder.encode("我们已给您的邮箱发送了验证邮件，完成验证后即可登录！(如果找不到邮件，请检查垃圾箱)", "utf-8");
    }

    // 激活用户
    @RequestMapping("/activate/account/{check_code}")
    public String activateAccount(@PathVariable("check_code") String check_code,
                                  HttpSession session) throws UnsupportedEncodingException {
        TempInfo tempInfo = tempInfoMapper.selectByCode(check_code);
        if (tempInfo == null) {
            return "redirect:/login.html?msg_info="+URLEncoder.encode("激活码不存在或用户已激活！","utf-8");
        }
        User newUser = new User();
        newUser.setUsername(tempInfo.getUsername());
        newUser.setEmail(tempInfo.getEmail());
        newUser.setPassword(tempInfo.getPassword());
        newUser.setNickname(tempInfo.getNickname());
        newUser.setSelf_intro(null);
        newUser.setAvatar_name(null);
        tempInfoMapper.delete(tempInfo);
        userMapper.insert(newUser);
        session.setAttribute("current_user", newUser);
        return "redirect:/user/setting.html";
    }
}
