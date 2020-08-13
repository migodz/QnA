package top.migod.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.migod.mapper.QuestionMapper;
import top.migod.pojo.Question;

import java.util.List;

@Repository
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    public List<Question> selectByUidAndPage(int uid, int page) {
        int offset = 9 * (page-1);
        int limit = 9;
        return questionMapper.selectByUidLimitOffset(uid,limit,offset);
    }

    public List<Question> selectByUid(int uid) {
        return questionMapper.selectByUid(uid);
    }

    public Question selectById(int qid) {
        return questionMapper.selectById(qid);
    }

    public int getPageNumByUid(int uid){
        int questionNum = questionMapper.getNumByUid(uid);
        if (questionNum == 0) return 1;
        return (int) ((questionNum-0.5)/9+1);
    }

    public int insert(Question question) {
        return questionMapper.insert(question);
    }

    public int delete(Question question) {
        return questionMapper.delete(question);
    }

}
