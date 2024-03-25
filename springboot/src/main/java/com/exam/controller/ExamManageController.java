package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.*;
import com.exam.serviceimpl.ExamManageServiceImpl;
import com.exam.util.ApiResultHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ExamManageController {

    @Autowired
    private ExamManageServiceImpl examManageService;

    @GetMapping("/exams")
    public ApiResult findAll(){
        System.out.println("不分页查询所有试卷");
        ApiResult apiResult;
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", examManageService.findAll());
        return apiResult;
    }

    @GetMapping("/exams/{page}/{size}")
    public ApiResult findAll(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("分页查询所有试卷");
        ApiResult apiResult;
        Page<ExamManage> examManage = new Page<>(page,size);
        IPage<ExamManage> all = examManageService.findAll(examManage);
        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
        return apiResult;
    }


    @GetMapping("/exam/{examCode}")
    public ApiResult findById(@PathVariable("examCode") Integer examCode){
        System.out.println("根据ID查找");
        ExamManage res = examManageService.findById(examCode);
        if(res == null) {
            return ApiResultHandler.buildApiResult(10000,"考试编号不存在",null);
        }
        return ApiResultHandler.buildApiResult(200,"请求成功！",res);
    }

    @DeleteMapping("/exam/{examCode}")
    public ApiResult deleteById(@PathVariable("examCode") Integer examCode){
        int res = examManageService.delete(examCode);
        return ApiResultHandler.buildApiResult(200,"删除成功",res);
    }

    @PutMapping("/exam")
    public ApiResult update(@RequestBody ExamManage exammanage){
        int res = examManageService.update(exammanage);
//        if (res == 0) {
//            return ApiResultHandler.buildApiResult(20000,"请求参数错误");
//        }
        System.out.print("更新操作执行---");
        return ApiResultHandler.buildApiResult(200,"更新成功",res);
    }

    @PostMapping("/exam")
    public ApiResult add(@RequestBody ExamManage exammanage){
        int res = examManageService.add(exammanage);
        if (res ==1) {
            return ApiResultHandler.buildApiResult(200, "添加成功", res);
        } else {
            return  ApiResultHandler.buildApiResult(400,"添加失败",res);
        }
    }

    @GetMapping("/examManagePaperId")
    public ApiResult findOnlyPaperId() {
        ExamManage res = examManageService.findOnlyPaperId();
        if (res != null) {
            return ApiResultHandler.buildApiResult(200,"请求成功",res);
        }
        return ApiResultHandler.buildApiResult(400,"请求失败",res);
    }



    @GetMapping("/assign_teache/{page}/{size}")
    public ApiResult assignTeache(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
        System.out.println("分页查询所有试卷");
        ApiResult apiResult;
        Page<ExamManage> examManage = new Page<>(page,size);
        IPage<ExamManage> all = examManageService.findAll(examManage);

        List<AssignTeacherVO> teacherList = new ArrayList<AssignTeacherVO>();
        for (ExamManage e: all.getRecords()) {
            int paperId = e.getPaperId();
            AssignTeacherVO assignTeacherVO = new AssignTeacherVO();
            BeanUtils.copyProperties(e, assignTeacherVO);
            assignTeacherVO.setTeacherNames(examManageService.findAssignTeacher(paperId));
            teacherList.add(assignTeacherVO);
        }
        MyPageResult myPageResult = new MyPageResult();
        myPageResult.setAssignTeacherVOS(teacherList);
        myPageResult.setCurrent(all.getCurrent());
        myPageResult.setSize(all.getSize());
        myPageResult.setTotal(all.getTotal());

        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", myPageResult);
        return apiResult;
    }

    @GetMapping("/exam/findTeachers")
    public ApiResult findTeachers(){
        List<Teacher> teachers = examManageService.findTeachers();
        return ApiResultHandler.buildApiResult(200, "请求成功", teachers);
    }

    @PostMapping("/exam/addAssignTeacher")
    public ApiResult addAssignTeacher(@RequestBody Map<String,Integer> ids){
        int res = examManageService.addAssignTeacher(ids);
        if (res == -1){
            return ApiResultHandler.buildApiResult(500, "重复添加", res );
        }
        return ApiResultHandler.buildApiResult(200, "添加成功", res );
    }

    @GetMapping("/exam/findSubjectTeachers/{paperId}")
    public ApiResult getSubjectTeachers(@PathVariable("paperId") Integer id){
//        System.out.println("分页查询所当前学科指定的老师");
//        ApiResult apiResult;
//        Page<ExamManage> examManage = new Page<>(page,size);
//        IPage<ExamManage> all = examManageService.findSubjectTeachers(examManage);
//        apiResult = ApiResultHandler.buildApiResult(200, "请求成功！", all);
//        return apiResult;
        return ApiResultHandler.buildApiResult(200, "查询成功", examManageService.findSubjectTeachers(id));
    }

    @DeleteMapping("/exam/delSubTeacher/{teacherId}/{paperId}")
    public ApiResult delSubTeacher(@PathVariable("teacherId") Integer teacherId,@PathVariable("paperId") Integer paperId){
        return ApiResultHandler.buildApiResult(200, "删除成功", examManageService.delSubTeacher(teacherId,paperId));
    }
}