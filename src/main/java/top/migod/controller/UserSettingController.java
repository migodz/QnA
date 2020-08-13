package top.migod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import top.migod.mapper.TempInfoMapper;
import top.migod.mapper.UserMapper;
import top.migod.pojo.TempInfo;
import top.migod.pojo.User;
import top.migod.util.MailUtil;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
public class UserSettingController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    TempInfoMapper tempInfoMapper;

    @Autowired
    MailUtil mailUtil;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Value("${qna.avatar_folder_path}")
    String avatarForderPath;

    @Value("${qna.avatar_folder_path.relative}")
    String avatarForderPathRelative;

    @Value("${qna.domain}")
    String domain;

    //用户资料页面跳转
    @RequestMapping({"/user/setting"})
    public String setting_redirect() {
        return "redirect:/user/setting.html";
    }

    //访问用户资料页面
    @RequestMapping("/user/setting.html")
    public String setting(Model model, HttpSession session) {
        model.addAttribute("current_user", session.getAttribute("current_user"));
        return "user_setting";
    }

    //个人资料表单验证
    @PostMapping("/user/setting_submit")
    public String setting_submit(@RequestParam("email") String email,
                                 @RequestParam("nickname") String nickname,
                                 @RequestParam("self_intro") String self_intro,
                                 HttpSession session,
                                 Model model) {
        User currentUser = (User) (session.getAttribute("current_user"));

        //如果修改了邮箱地址，创建临时数据并发送验证邮件
        if (!currentUser.getEmail().equals(email)) {
            TempInfo tempInfo;
            //检查临时表中是否存在相同请求的历史数据，如果有则直接修改原始数据
            if (tempInfoMapper.selectByUid(currentUser.getUid()) != null) {
                tempInfo = tempInfoMapper.selectByUid(currentUser.getUid());
                tempInfo.setEmail(email);
                tempInfoMapper.update(tempInfo);
            } else { //如果没有，储存邮箱地址到临时表，生成新邮箱验证码
                tempInfo = new TempInfo();
                tempInfo.setType(2); //邮箱验证临时数据
                tempInfo.setUid(currentUser.getUid());
                tempInfo.setEmail(email);

                //产生8位数字验证码
                String check_code = String.valueOf((int) (89999999 * Math.random() + 10000000));
                System.out.println(check_code);
                while (tempInfoMapper.selectByCode(check_code) != null) {
                    check_code = String.valueOf((int) (89999999 * Math.random() + 10000000));
                }
                tempInfo.setCheck_code(check_code);
                tempInfoMapper.insert(tempInfo);
            }
            //发送激活邮件
            Context context = new Context();
            context.setVariable("title", "邮箱验证");
            context.setVariable("content", "点击下方的超链接，验证之后即可启用新邮箱！如果这不是您的请求，请忽视这封邮件。（自动发送，请勿回复）");
            context.setVariable("url", "http://" + domain + "/activate/email/" + tempInfo.getCheck_code());
            context.setVariable("url_text", "点击激活");
            String mailContent = templateEngine.process("mail_notice", context);
            mailUtil.sendMail(email, "QnA邮箱验证 - QnA", mailContent);
        }

        // 保存邮箱地址之外的其他数据
        currentUser.setNickname(nickname);
        currentUser.setSelf_intro(self_intro);
        userMapper.update(currentUser);

        model.addAttribute("msg_info", "个人资料已保存！");
        session.setAttribute("current_user", currentUser);
        return "user_setting";
    }

    //新邮箱地址验证
    @RequestMapping("/activate/email/{check_code}")
    public String activateEmail(@PathVariable("check_code") String check_code,
                                HttpSession session,
                                Model model) {
        TempInfo tempInfo = tempInfoMapper.selectByCode(check_code);
        if (tempInfo == null) {
            return "redirect:/user/setting.html";
        }
        User user = userMapper.selectById(tempInfo.getUid());
        user.setEmail(tempInfo.getEmail());
        userMapper.update(user);
        tempInfoMapper.delete(tempInfo);
        session.setAttribute("current_user", user);
        model.addAttribute("msg_info", "新邮箱已启用！");
        return "user_setting";
    }

    //密码表单验证
    @PostMapping("/user/password_reset_submit")
    public String reset_password(@RequestParam("password") String password,
                                 @RequestParam("new_password") String new_password,
                                 @RequestParam("new_password_confirm") String new_password_confirm,
                                 HttpSession session,
                                 Model model) {
        User currentUser = (User) (session.getAttribute("current_user"));
        System.out.println(currentUser);
        currentUser = userMapper.selectByUsername(currentUser.getUsername());
        System.out.println(currentUser);

        if (!password.equals(currentUser.getPassword())) {
            model.addAttribute("msg", "密码错误！");
            model.addAttribute("current_user", currentUser);
            return "user_setting";
        }
        if (!new_password.equals(new_password_confirm)) {
            model.addAttribute("msg", "两次密码填写不一致！");
            model.addAttribute("current_user", currentUser);
            return "user_setting";
        }

        currentUser.setPassword(new_password);
        userMapper.update(currentUser);

        model.addAttribute("msg_info", "密码变更成功！");
        session.setAttribute("current_user", currentUser);
        return "user_setting";
    }

    //用户头像上传
    @PostMapping("/user/set_profile_picture")
    public String fileUpload2(@RequestParam("profile_picture") MultipartFile profilePic,
                              HttpSession session,
                              Model model) throws IOException {
        if (profilePic.isEmpty()) {
            model.addAttribute("msg", "头像上传错误");
            return "user_setting";
        }

        //上传路径保存设置
        File realPath = new File(avatarForderPath);
        if (!realPath.exists()) {
            realPath.mkdir();
        }

        User currentUser = ((User) session.getAttribute("current_user"));
        String[] temps = profilePic.getOriginalFilename().split("\\.");
        String filetype = temps[temps.length - 1];

        if (!"jpg".equals(filetype)) {
            model.addAttribute("msg", "文件格式错误，请上传jpg格式的头像文件！");
            return "user_setting";
        }

        File avatar = new File(avatarForderPath + currentUser.getUsername() + "." + filetype);
        if (avatar.exists()) {
            avatar.delete();
        }
        String relativePath = avatarForderPathRelative.substring(0, avatarForderPathRelative.length() - 2);
        currentUser.setAvatar_name(relativePath + currentUser.getUsername() + "." + filetype);
        profilePic.transferTo(avatar);

        userMapper.update(currentUser);
        model.addAttribute("msg_info", "头像上传成功");
        return "user_setting";
    }
}
