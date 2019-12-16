/**
 * All rights Reserved, Designed By ZC.LangFang
 * @Title:  ResultBody.java
 * @author: XueYang.Li
 * @date:   2019/3/5 5:00 PM
 * @version V1.0
 * @Copyright: 2019/3/5 INM Inc. All rights reserved.
 * 注意：本内容仅限于河北志晟信息技术股份有限公司内部传阅，禁止外泄以及用于其他的商业目
 */
package com.zcx.test.common;

import lombok.Data;

/**
 * @Title: ResultBody
 * @Package: com.zc.partymember.common
 * @Describe: 通用结果集
 * @author: XueYang.Li
 * @date: 2019/3/5 5:00 PM
 * @version: V1.0
 **/
@Data
public class ResultBody {

    private Integer code;
    private String msg;
    private Long count;
    private Object data;

    public ResultBody success(Object data) {
        ResultBody resultBody = new ResultBody();
        resultBody.setCode(Consts.SUCCESS);
        resultBody.setMsg("");
        resultBody.setData(data);
        return resultBody;
    }

    public ResultBody success(Object data,String msg) {
        ResultBody resultBody = new ResultBody();
        resultBody.setCode(Consts.SUCCESS);
        resultBody.setMsg(msg);
        resultBody.setData(data);
        return resultBody;
    }

    public ResultBody failure(String msg) {
        ResultBody resultBody = new ResultBody();
        resultBody.setCode(Consts.FAILURE);
        resultBody.setData(null);
        resultBody.setMsg(msg);
        return resultBody;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
