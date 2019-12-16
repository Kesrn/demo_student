package com.zcx.test.common;

public interface ApiConst {
    String STATIC_JS = "/**/*.js";
    String STATIC_CSS = "/**/*.css";

    String ALL_1 = "/*";
    String ALL_2 = "/**";
    String ALL_3 = "/*.*";

    String API_LOGIN = "/login";
    String API_LOGOUT = "/logout";
    String API_HOME_CONSOLE = "/home";


    String API_USER = "/user";
    String API_USER_TEST = "/test";
    String API_USER_LIST = "/userlist";
    String API_USER_CREATE = "/create";
    String API_USER_UPDATE = "/{id}/update";
    String API_USER_DELETE = "/{id}/delete";
    String API_USER_PWD_CHANGE = "/{id}/changePassword";


    String API_STUDENT = "/student";
    String API_STUDENT_CREATE = "/create";
    String API_STUDENT_LIST = "/memberList";
    String API_STUDENT_UPDATE = "/{id}/update";
    String API_STUDENT_DELETE = "/{id}/delete";

    /**
     * 生成文件存放路径
     */
    public static final String FILE_PATH = "C:\\Users\\Administrator\\Desktop\\";

    /**
     * 表格默认名称
     */
    public static final String FILE_NAME = "TEST.xls";
}
