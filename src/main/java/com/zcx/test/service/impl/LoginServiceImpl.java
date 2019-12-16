package com.zcx.test.service.impl;

import com.zcx.test.dao.UserMapper;
import com.zcx.test.entity.User;
import com.zcx.test.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper mapper;

    @Transactional(readOnly = true)
    public User selectByName(String user) {

        return mapper.selectByName(user);
    }
}
