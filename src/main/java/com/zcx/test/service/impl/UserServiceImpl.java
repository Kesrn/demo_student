package com.zcx.test.service.impl;

import com.zcx.test.dao.UserMapper;
import com.zcx.test.entity.Student;
import com.zcx.test.entity.User;
import com.zcx.test.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;

    public User selectByName(String name){

      return mapper.selectByName(name);

    }

    @Override
    public User findOne(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<User> listAll(String username){

        return mapper.selectAll(username);
    }

    public int insertUser(User user){
        String md5 = "MD5";
        String _salt = ByteSource.Util.bytes(user.getUsername()).toString();
        Object salt = ByteSource.Util.bytes(_salt);
        int hasIter = 1024;

        Object result = new SimpleHash(md5, "123456", salt, hasIter);
        user.setPassword(result.toString());
        user.setStatus("0");
        user.setSalt(_salt);

        User user1 = (User) SecurityUtils.getSubject().getPrincipal();
        user.setCreateBy(user1.getId());
        user.setCreateDate(new Date());
        user.setUpdateBy(user1.getId());
        user.setUpdateDate(new Date());
        return mapper.insertSelective(user);
    }

    public int deleteUser(int id){
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(User user) {
        return mapper.updateByPrimaryKeySelective(user);
    }


}
