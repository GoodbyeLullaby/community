package com.ustc.community.mapper;

import com.ustc.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);
}