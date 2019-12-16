package com.zcx.test.service;

import com.zcx.test.entity.User;

public interface LoginService {

    User selectByName(String user);
}
