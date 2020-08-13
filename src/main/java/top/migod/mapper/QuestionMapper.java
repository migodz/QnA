package top.migod.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.migod.pojo.Question;
import top.migod.pojo.User;

import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {
    List<Question> selectAll();
    List<Question> selectByUid(int uid);
    List<Question> selectByUidLimitOffset(int uid, int limit, int offset);
    Question selectById(int qid);
    int getNumByUid(int uid);
    int insert(Question question);
    int update(Question question);
    int delete(Question question);
}
