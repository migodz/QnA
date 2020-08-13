package top.migod.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.migod.pojo.User;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> selectAll();
    User selectById(int uid);
    User selectByUsername(String username);
    int insert(User user);
    int update(User user);
    int delete(User user);
}
