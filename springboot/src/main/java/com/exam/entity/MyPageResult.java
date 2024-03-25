package com.exam.entity;

import com.exam.entity.AssignTeacherVO;
import lombok.Data;

import java.util.List;

@Data
public class MyPageResult {
    private List<AssignTeacherVO> assignTeacherVOS;
    private long total;
    private long size;
    private long current;
}
