package top.migod.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.migod.pojo.TempInfo;

@Mapper
@Repository
public interface TempInfoMapper {
    TempInfo selectByCode(String check_code);
    TempInfo selectByUsername(String username);
    TempInfo selectByUid(int uid);
    int insert(TempInfo tempInfo);
    int update(TempInfo tempInfo);
    int delete(TempInfo tempInfo);
}
