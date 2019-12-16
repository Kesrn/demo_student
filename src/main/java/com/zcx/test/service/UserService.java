package com.zcx.test.service;

import com.zcx.test.entity.User;

import java.util.List;

public interface UserService {
    /**
     *
     *
     * @description: 根据用户姓名查询用户信息
     * @param user
     * @return: 2019/12/11
     * @author: Andy
     * @time: 11:32
     */
    User selectByName(String user);
    /**
     *
     *
     * @description: 根据id查询用户信息
     * @param id
     * @return: 2019/12/11
     * @author: Andy
     * @time: 11:31
     */
    User findOne(Integer id);

    /**
     *
     *
     * @description: 显示用户列表
     * @param username
     * @return: 2019/12/11
     * @author: Andy
     * @time: 11:31
     */
    List<User> listAll(String username);


    /**
     *
     *
     * @description: 新增用户
     * @param user
     * @return: 2019/12/11
     * @author: Andy
     * @time: 11:23
     */
    int insertUser(User user);
    /**
     *
     *
     * @description: 删除用户
     * @param id
     * @return: 2019/12/11
     * @author: Andy
     * @time: 15:39
     */
    int deleteUser(int id);
    /**
     *
     *
     * @description: 修改用户信息
     * @param user
     * @return: 2019/12/11
     * @author: Andy
     * @time: 16:15
     */
    int update(User user);

    
}
