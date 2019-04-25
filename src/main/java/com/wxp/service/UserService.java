package com.wxp.service;

import com.wxp.bean.User;
import com.wxp.common.util.PasswordUtil;
import com.wxp.dao.UserDAO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * create by ff on 2018/6/23
 */
@Service
public class UserService {

    @Resource
    private UserDAO userDAO;

    public Set<String> getRoles(String userName){

        return userDAO.findRoles(userName);
    }

    public Set<String> getPremissions(String userName){
        return userDAO.findPermissions(userName);
    }

    public User findByUsername(String userName) {
        return userDAO.findUser(userName);
    }

    /**
     * 新建用户
     * @param user
     * @return
     */
    public User createUser(User user) {
        PasswordUtil.encryptPassword(user);//生成数据库密码
        user.setLocked("1");
        boolean result = userDAO.createUser(user);
        return user;
    }


}
