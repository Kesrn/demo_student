package com.zcx.test.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcx.test.common.Consts;
import com.zcx.test.common.ResultBody;
import com.zcx.test.entity.Student;
import com.zcx.test.service.StudentService;

import com.zcx.test.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @InitBinder
    protected void init(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping()
    public String listAll() {
        return "student/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultBody onlist(@RequestParam(name = "page") Integer currPage,
                             @RequestParam("limit") Integer pageSize) {
        ResultBody resultBody = new ResultBody();
        PageHelper.startPage(currPage, pageSize);
        List<Student> list = studentService.listAll();
        try {
            for (Student student : list) {
                if (student.getSex().equals("0")) {
                    student.setSex("男");
                } else {
                    student.setSex("女");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        PageInfo<Student> pageInfo = new PageInfo<>(list);
        resultBody.setCode(Consts.SUCCESS);
        resultBody.setMsg("");
        resultBody.setData(list);
        resultBody.setCount(Long.valueOf(pageInfo.getTotal()));

        return resultBody;
    }

    @RequestMapping("/add")
    public String insert() {
        return "student/add";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public ResultBody insertEmp(Student student) {
        ResultBody resultBody = new ResultBody();
        try {
            studentService.insert(student);
            resultBody = resultBody.success(new ArrayList<>(), "新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultBody.failure("系统繁忙，请稍后再试！");
        }
        return resultBody;
    }

    @RequestMapping("/{id}/delete")
    @ResponseBody
    public ResultBody deleteEmp(@PathVariable("id") Integer id) {
        ResultBody resultBody = new ResultBody();
        try {
            studentService.delete(id);
            resultBody = resultBody.success(new ArrayList<>(), "删除成功！");
        } catch (Exception e) {
            e.printStackTrace();
            resultBody = resultBody.failure("系统繁忙，请稍后再试！");
        }
        return resultBody;
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id,Model model){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Student student = studentService.findOne(id);
        try {
            student.setBirthdate(sf.parse(sf.format(student.getBirthdate())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("student",student);
        return "student/edit";
    }

    @RequestMapping("/{id}/update")
    @ResponseBody
    public ResultBody updateEmp(Student student){
        ResultBody resultBody = new ResultBody();
        try {
            studentService.update(student);
            resultBody = resultBody.success(new ArrayList<>(),"修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            resultBody = resultBody.failure("系统繁忙，请稍后再试！");
        }
        return resultBody;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public ResultBody onUpload(@RequestParam("file") MultipartFile uploadFile, HttpServletRequest request) {
        ResultBody resultBody = new ResultBody();
        String suffix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
        String savePath = "userfiles/pic/" + DateUtils.getYear() + "/" + DateUtils.getMonth() + "/";
        String path = System.getProperty("user.dir") + "/profile/" + savePath;
        String saveName = (int) ((Math.random() * 9 + 1) * 100000) + suffix;
        //新文件存储路径
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        //保存
        try {
            uploadFile.transferTo(new File(path + saveName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("filepath", savePath + saveName);
        return resultBody.success(map, "上传成功");
    }

}
