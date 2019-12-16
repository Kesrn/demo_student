package com.zcx.test.service;

public interface ResetPwdService {
    boolean resetpassword(String pwold, String pwnew, String username) throws Exception;
    boolean initpwd() throws Exception;
    boolean setpassword(String pwnew, String username);
}
