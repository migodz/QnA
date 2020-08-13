package top.migod.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.migod.pojo.Answer;

import java.util.List;

@Mapper
@Repository
public interface AnswerMapper {
    List<Answer> selectAll();
    Answer selectById(int aid);
    Answer selectByQid(int qid);
    int insert(Answer answer);
    int update(Answer answer);
    int delete(Answer answer);
}
