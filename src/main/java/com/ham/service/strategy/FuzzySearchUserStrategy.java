package com.ham.service.strategy;

import com.ham.dao.UserMapper;
import com.ham.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by hamsbon on 2017/3/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class FuzzySearchUserStrategy extends BaseUserStrategy {

    @Autowired
    private UserMapper userMapper;

    @Override
    protected List<UserVO> buildUserVo(Object param) {
        List<UserVO> result = null;
        if(param instanceof String){
            String idStr = (String) param;
            result = userMapper.searchByKeywordWithFullText(idStr);
            if(result.size()<1){
                result = userMapper.searchByKeywordWithLike(idStr);
            }
        }
        return result;
    }
}
