package com.exam.mapper;

import com.exam.entity.PaperManage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PaperMapper {
    @Select("select paperId, questionType,questionId from paper_manage")
    List<PaperManage> findAll();

    @Select("select paperId, questionType,questionId from paper_manage where paperId = #{paperId}")
    List<PaperManage> findById(Integer paperId);

    @Insert("insert into paper_manage(paperId,questionType,questionId) values " +
            "(#{paperId},#{questionType},#{questionId})")
    int add(PaperManage paperManage);

    @Select("select * from paper_manage where paperId=#{paperId} and questionType=#{questionType}")
    List<PaperManage> isHave(Integer paperId, Integer questionType);

    @Delete("delete from paper_manage where paperId=#{paperId} and questionType=#{questionType}")
    void delExam(Integer paperId, Integer questionType);
}
