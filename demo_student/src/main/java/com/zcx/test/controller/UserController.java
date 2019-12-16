package com.zcx.test.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcx.test.common.Consts;
import com.zcx.test.common.ResultBody;
import com.zcx.test.entity.User;
import com.zcx.test.service.UserService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;


    @GetMapping()
    public String list(Model model){
        Subject subject = SecurityUtils.getSubject();
       User user = (User) subject.getPrincipal();
       model.addAttribute("user",user);
        return "user/list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResultBody onlist(@RequestParam(name = "page") Integer currPage,
                             @RequestParam("limit") Integer pageSize,
                             @RequestParam(value = "username",required = false)String username){
        ResultBody resultBody = new ResultBody();
        try {
        PageHelper.startPage(currPage,pageSize);
        List<User> list = service.listAll(username);

        for (User user : list) {
            if (user.getSex().equals("0")) {
                user.setSex("男");
            } else {
                user.setSex("女");
            }
        }
        PageInfo<User> pageInfo = new PageInfo<>(list);
        resultBody.setCode(Consts.SUCCESS);
        resultBody.setMsg("查询成功！");
        resultBody.setData(list);
        resultBody.setCount(Long.valueOf(pageInfo.getTotal()));
    }catch (Exception e){
        e.printStackTrace();
        resultBody = resultBody.failure("系统繁忙，请稍后重试！");
    }


        return resultBody;
    }

    @GetMapping("/add")
    public String insert(Model model){

        return "user/add";
    }
    @PostMapping("/create")
    @ResponseBody
    public ResultBody insertUser(User user){
        ResultBody resultBody = new ResultBody();
        try {
            service.insertUser(user);
            resultBody = resultBody.success(new ArrayList<>(), "新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultBody.failure("系统繁忙，请稍后再试！");
        }
        return resultBody;

    }

    @RequestMapping("/{id}/delete")
    @ResponseBody
    public ResultBody deleteUser(@PathVariable("id")Integer id){
        ResultBody resultBody = new ResultBody();
        try {
            service.deleteUser(id);
            resultBody = resultBody.success(new ArrayList<>(), "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultBody.failure("系统繁忙，请稍后再试！");
        }
        return resultBody;
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id")Integer id, Model model){
        User user = service.findOne(id);
        model.addAttribute("user",user);
        return "user/edit";
    }
    @RequestMapping("/{id}/update")
    @ResponseBody
    public ResultBody edit(User user){
        ResultBody resultBody = new ResultBody();
        try {
            service.update(user);
            resultBody = resultBody.success(new ArrayList<>(), " 修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultBody.failure("系统繁忙，请稍后再试！");
        }
        return resultBody;
    }

}
