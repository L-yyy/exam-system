package com.exam.entity;

import lombok.Data;

import java.util.List;

@Data
public class AssignTeacherVO extends ExamManage{

    private Integer id;

    private Integer teacherId;

    private Integer paperId;

    private String teacherName;

    private List<String> teacherNames;
}
