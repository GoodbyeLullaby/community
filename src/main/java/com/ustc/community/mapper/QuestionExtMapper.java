package com.ustc.community.mapper;

import com.ustc.community.model.Comment;
import com.ustc.community.model.Question;
import com.ustc.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incComment(Question record);
}