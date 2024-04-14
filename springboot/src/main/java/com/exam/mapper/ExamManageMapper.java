package com.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.AssignTeacherVO;
import com.exam.entity.ExamManage;
import com.exam.entity.SubjectTeacher;
import com.exam.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExamManageMapper extends BaseMapper {
    @Select("select * from exam_manage")
    List<ExamManage> findAll();

    @Select("select * from exam_manage")
    IPage<ExamManage> findAll(Page page);

    @Select("select * from exam_manage where examCode = #{examCode}")
    ExamManage findById(Integer examCode);

    @Delete("delete from exam_manage where examCode = #{examCode}")
    int delete(Integer examCode);

    @Update("update exam_manage set description = #{description},source = #{source},paperId = #{paperId}," +
            "examDate = #{examDate},totalTime = #{totalTime},grade = #{grade},term = #{term}," +
            "major = #{major},institute = #{institute},totalScore = #{totalScore}," +
            "type = #{type},tips = #{tips} where examCode = #{examCode}")
    int update(ExamManage exammanage);

    @Options(useGeneratedKeys = true,keyProperty = "examCode")
    @Insert("insert into exam_manage(description,source,paperId,examDate,totalTime,grade,term,major,institute,totalScore,type,tips)" +
            " values(#{description},#{source},#{paperId},#{examDate},#{totalTime},#{grade},#{term},#{major},#{institute},#{totalScore},#{type},#{tips})")
    int add(ExamManage exammanage);

    /**
     * 查询最后一条记录的paperId,返回给前端达到自增效果
     * @return paperId
     */
    @Select("select paperId from exam_manage order by paperId desc limit 1")
    ExamManage findOnlyPaperId();

    @Select("select teacherName from subject_teacher where paperId=#{paperid}")
    List<String> findAssignTeachers(int paperId);

    @Select("select teacherName,teacherId from teacher")
    List<Teacher> findTeachers();

    @Insert("insert into subject_teacher(teacherId,paperId,teacherName) values (#{selectedTeacherId},#{paperId},#{selectedTeacherName})")
    int addTheacherToSubject(int paperId, int selectedTeacherId, String selectedTeacherName);

    @Select("select teacherName from teacher where teacherId=#{selectedTeacherId}")
    String findTeacherName(int selectedTeacherId);

    @Select("select teacherId,paperId,teacherName from subject_teacher where paperId=#{id}")
    List<AssignTeacherVO> findSubkectTeachers(Integer id);

    @Select("select source from exam_manage where paperId=#{paperId}")
    String getSourceByPaperId(int paperId);

    @Delete("delete from subject_teacher where teacherId=#{teacherId} and paperId=#{paperId}")
    int delSubTeacher(Integer teacherId, Integer paperId);

    @Select("select * from subject_teacher where teacherId=#{selectedTeacherId} and paperId=#{paperId}")
    List<AssignTeacherVO> isHaveTea(int selectedTeacherId, int paperId);

    @Select("select * from subject_teacher where teacherId=#{cid}")
    List<SubjectTeacher> findSubjectByCid(Integer cid);

    @Select("<script>" +
            "SELECT * FROM manage " +
            "WHERE paper_id IN " +
            "<foreach item='paperId' collection='paperIds' open='(' separator=',' close=')'>" +
            "#{paperId}" +
            "</foreach>" +
            "</script>")
    IPage<ExamManage> findAllByPaperIds();

    @Select("<script>" +
            "SELECT * FROM exam_manage " +
            "WHERE paperId IN " +
            "<foreach item='paperId' collection='paperIds' open='(' separator=',' close=')'>" +
            "#{paperId}" +
            "</foreach>" +
            "</script>")
    List<ExamManage> finAllSub(@Param("paperIds") List<Integer> paperIds);
}
