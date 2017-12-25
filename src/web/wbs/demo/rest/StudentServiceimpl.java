package com.hkc.mdw.web.wbs.demo.rest;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import com.hkc.mdw.web.entity.demo.StudentDateSet;
import com.hkc.mdw.web.entity.demo.StudentListVo;
import com.hkc.mdw.web.entity.demo.StudentVo;
@Component("studentService")
public class StudentServiceimpl implements StudentService {

    public String getStatus() {
        return "this is cxf rest service, status is normal!我是中国人";
    }

    public StudentVo getStudentById(@PathParam("index") Integer id) {
        List<StudentVo> studentList = StudentDateSet.getList();
        StudentVo vo = null;
        if (studentList.size() > id) {
            vo = studentList.get(id - 1);
        }
        return vo;
    }

    public StudentListVo getStudentList() {
        List<StudentVo> studentList = StudentDateSet.getList();
        StudentListVo listVo = new StudentListVo(studentList);
        return listVo;
    }
}
