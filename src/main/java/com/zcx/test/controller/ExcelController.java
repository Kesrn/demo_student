package com.zcx.test.controller;

import com.zcx.test.common.ResultBody;
import com.zcx.test.entity.Student;
import com.zcx.test.service.StudentService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/excel")
public class ExcelController {


    @Autowired
    private StudentService studentService;
    /**
     * Excel表格导出接口
     * http://localhost:8080/ExcelDownload
     * @param response response对象
     * @throws IOException 抛IO异常
     */
    @RequestMapping("/StudentexcelDownload")
    public void excelDownload(HttpServletResponse response) throws IOException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");


        List<Student> students = studentService.listAll();
        //表头数据
        String[] header = {"学生姓名", "学生性别", "出生日期","家庭住址"};
        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称为"学生表"
        HSSFSheet sheet = workbook.createSheet("学生表");

        //设置表格列宽度为10个字节
        sheet.setDefaultColumnWidth(10);

        //创建第一行表头
        HSSFRow headrow = sheet.createRow(0);

        //遍历添加表头(下面模拟遍历学生，也是同样的操作过程)
        for (int i = 0; i < header.length; i++) {
            //创建一个单元格
            HSSFCell cell = headrow.createCell(i);


            //创建一个内容对象
            HSSFRichTextString text = new HSSFRichTextString(header[i]);

            //将内容对象的文字内容写入到单元格中
            cell.setCellValue(text);
        }
        //模拟遍历结果集，把内容加入表格
        //模拟遍历第一个学生
        for (int i = 1; i < students.size(); i++) {
            HSSFRow row1 = sheet.createRow(i);
            HSSFCell cell1=row1.createCell(0);
            cell1.setCellValue(students.get(i).getStuname());
            HSSFCell cell2=row1.createCell(1);
            cell2.setCellValue(students.get(i).getSex().equals("0")?"男":"女");
            HSSFCell cell3=row1.createCell(2);
            cell3.setCellValue(sf.format(students.get(i).getBirthdate()));
            HSSFCell cell4=row1.createCell(3);
            cell4.setCellValue(students.get(i).getAddress());
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //这后面可以设置导出Excel的名称，此例中名为student.xls
        response.setHeader("Content-disposition", "attachment;filename=student"+sf.format(new Date())+".xls");

        //刷新缓冲
        response.flushBuffer();

        //workbook将Excel写入到response的输出流中，供页面下载
        workbook.write(response.getOutputStream());
        System.out.println("");

    }
    @RequestMapping("/inputExcel")
    @ResponseBody
    public ResultBody inputExcel(@RequestParam(value = "filepath",required = false) String filepath) throws FileNotFoundException {
        ResultBody resultBody = new ResultBody();
        FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/profile/" + filepath);
        try {
            Student student = new Student();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            int columnNum=sheet.getRow(0).getPhysicalNumberOfCells();
            int rowNum=sheet.getLastRowNum();
            if(columnNum == 4) {
                for (Integer i = 1; i <= rowNum; i++) {
                    SimpleDateFormat sf = new SimpleDateFormat();
                    HSSFRow row = sheet.getRow(i);


                    String stuname = row.getCell(0).getStringCellValue();
                    String sex  = row.getCell(1).getStringCellValue();
                    HSSFCell cell3 = row.getCell(2);
                    Date birthday = null;
                    if (cell3 == null) {

                    } else {
                        birthday = cell3.getDateCellValue();
                    }
                    String address = row.getCell(3).getStringCellValue();


                    student.setStuname(stuname);
                    student.setSex(sex.equals("男")?"0":"1");
                    student.setBirthdate(birthday);
                    student.setAddress(address);

                    studentService.insert(student);
                }
                resultBody =  resultBody.success(new ArrayList<>(),"导入成功！");
            }else {
                resultBody = resultBody.failure("导入格式错误，请您检查excel的格式！");
            }

        } catch (IOException e) {
            e.printStackTrace();
            resultBody = resultBody.failure("导入失败！");
        }

            return  resultBody;
    }

}
