package top.migod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import top.migod.mapper.AnswerMapper;
import top.migod.mapper.QuestionMapper;
import top.migod.mapper.UserMapper;
import top.migod.pojo.Answer;
import top.migod.pojo.Question;
import top.migod.pojo.User;
import top.migod.util.MailUtil;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class QuestionAnswerController {

    @Value("${qna.domain}")
    String domain;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    AnswerMapper answerMapper;

    @Autowired
    MailUtil mailUtil;

    @Autowired
    SpringTemplateEngine templateEngine;

    //跳转提问页面
    @RequestMapping("/q/{qid}")
    public String question(@PathVariable("qid") int qid) {
        return "redirect:/q/" + qid + ".html";
    }

    //访问提问(回答)页面
    @RequestMapping("/q/{qid}.html")
    public String question(@PathVariable("qid") int qid,
                           Model model) {
        Question q = questionMapper.selectById(qid);
        if (q == null) {
            return "error/404";
        }
        model.addAttribute("question", q);

        Answer a = answerMapper.selectByQid(qid);
        if (a != null) {
            model.addAttribute("answer", a);
        }
        model.addAttribute("view_user", userMapper.selectById(q.getUid()));
        return "question";
    }

    //发布新提问
    @PostMapping("/new_question")
    public String newQuestion(@RequestParam("uid") int uid,
                              @RequestParam("content") String content) {

        //储存提问
        Date date = new Date();
        Question question = new Question();
        question.setUid(uid);
        question.setContent(content);
        question.setTime(date);
        questionMapper.insert(question);

        //对版主进行邮件提醒
        User user = userMapper.selectById(question.getUid());
        Context context=new Context();
        context.setVariable("question",question);
        context.setVariable("url","http://"+domain+"/q/"+question.getQid()+".html");
        String mailContent = templateEngine.process("mail_question_notice",context);
        mailUtil.sendMail(user.getEmail(),"您收到了新的提问！ - QnA", mailContent);

        return "redirect:/q/" + question.getQid() + ".html";
    }

    //发布新回答
    @PostMapping("/new_answer")
    public String newAnswer(@RequestParam("qid") int qid,
                            @RequestParam("content") String content,
                            HttpSession session) {
        Question question = questionMapper.selectById(qid);
        User currentUser = (User) session.getAttribute("current_user");
        if (currentUser == null || currentUser.getUid() != question.getUid()) {
            return "redirect:/q/" + qid + ".html";
        }
        Date date = new Date();
        Answer answer = new Answer();
        answer.setUid(null); //暂不支持实名回答
        answer.setQid(qid);
        answer.setContent(content);
        answer.setTime(date);
        answerMapper.insert(answer);
        return "redirect:/q/" + qid + ".html";
    }

    //删除回答
    @PostMapping("/delete_answer")
    public String deleteAnswer(@RequestParam("aid") int aid,
                               HttpSession session) {
        Answer answer = answerMapper.selectById(aid);

        if (answer == null) {
            return "error/404";
        }

        User currentUser = (User) session.getAttribute("current_user");
        int questionUserID = questionMapper.selectById(answer.getQid()).getUid();
        if (currentUser == null || currentUser.getUid() != questionUserID) {
            return "redirect:/q/" + answer.getQid() + ".html";
        }

        answerMapper.delete(answer);
        return "redirect:/q/" + answer.getQid() + ".html";
    }

    //修改回答
    @RequestMapping("/reset_answer")
    public String resetAnswer(@RequestParam("aid") int aid,
                            @RequestParam("content") String content,
                            HttpSession session) {
        Answer answer = answerMapper.selectById(aid);
        User currentUser = (User) session.getAttribute("current_user");
        if (currentUser == null || currentUser.getUid() != questionMapper.selectById(answer.getQid()).getUid()) {
            return "redirect:/q/" + answer.getQid() + ".html";
        }
        Date date = new Date();
        answer.setContent(content);
        answer.setTime(date);
        answerMapper.update(answer);
        return "redirect:/q/" + answer.getQid() + ".html";
    }
}
