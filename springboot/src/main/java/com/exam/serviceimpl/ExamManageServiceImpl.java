package com.exam.serviceimpl;

//import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.AssignTeacherVO;
import com.exam.entity.ExamManage;
import com.exam.entity.Teacher;
import com.exam.mapper.ExamManageMapper;
import com.exam.service.ExamManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExamManageServiceImpl implements ExamManageService {
    @Autowired
    private ExamManageMapper examManageMapper;

    @Override
    public List<ExamManage> findAll() {
        return examManageMapper.findAll();
    }

    @Override
    public IPage<ExamManage> findAll(Page<ExamManage> page) {
        return examManageMapper.findAll(page);
    }

    @Override
    public ExamManage findById(Integer examCode) {
        return examManageMapper.findById(examCode);
    }

    @Override
    public int delete(Integer examCode) {
        return examManageMapper.delete(examCode);
    }

    @Override
    public int update(ExamManage exammanage) {
        return examManageMapper.update(exammanage);
    }

    @Override
    public int add(ExamManage exammanage) {
        return examManageMapper.add(exammanage);
    }

    @Override
    public ExamManage findOnlyPaperId() {
        return examManageMapper.findOnlyPaperId();
    }

    @Override
    public List<String> findAssignTeacher(int paperId) {
        return examManageMapper.findAssignTeachers(paperId);
    }

    @Override
    public List<Teacher> findTeachers() {
        return examManageMapper.findTeachers();
    }

    @Override
    public int addAssignTeacher(Map<String, Integer> ids) {
        int paperId = Integer.parseInt(ids.get("paperId").toString());
        int selectedTeacherId = Integer.parseInt(ids.get("selectedTeacherId").toString());

        List<AssignTeacherVO> vo = examManageMapper.isHaveTea(selectedTeacherId, paperId);
        if (vo.size() != 0){
           return -1;
        }

        String selectTeacherName = examManageMapper.findTeacherName(selectedTeacherId);
        return examManageMapper.addTheacherToSubject(paperId, selectedTeacherId, selectTeacherName);
    }

    @Override
    public List<AssignTeacherVO> findSubjectTeachers(Integer id) {
        List<AssignTeacherVO> subkectTeachers = examManageMapper.findSubkectTeachers(id);

        int paperId = 0;
        if (subkectTeachers != null){
            paperId = subkectTeachers.get(0).getPaperId();
        }
        String source = examManageMapper.getSourceByPaperId(paperId);

        for (AssignTeacherVO e: subkectTeachers) {
            e.setSource(source);
        }

        return subkectTeachers;
    }

    @Override
    public int delSubTeacher(Integer teacherId,Integer paperId) {
        return examManageMapper.delSubTeacher(teacherId, paperId);
    }


}
