package com.exam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.AssignTeacherVO;
import com.exam.entity.ExamManage;
import com.exam.entity.SubjectTeacher;
import com.exam.entity.Teacher;

import java.util.List;
import java.util.Map;

public interface ExamManageService {

    /**
     * 不分页查询所有考试信息
     */
    List<ExamManage> findAll();
    IPage<ExamManage> findAll(Page<ExamManage> page);

    ExamManage findById(Integer examCode);

    int delete(Integer examCode);

    int update(ExamManage exammanage);

    int add(ExamManage exammanage);

    ExamManage findOnlyPaperId();

    List<String> findAssignTeacher(int paperId);

    List<Teacher> findTeachers();

    int addAssignTeacher(Map<String, Integer> ids);

    List<AssignTeacherVO> findSubjectTeachers(Integer id);

    int delSubTeacher(Integer teacherId, Integer paperId);

    List<SubjectTeacher> findSubjectByCid(Integer cid);

    IPage<ExamManage> findAllByPaperIds(Page<ExamManage> page, List<Integer> paperIds);

    List<ExamManage> findAllSub(List<Integer> paperIds);
}
