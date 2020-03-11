package com.ustc.community.mapper;

import com.ustc.community.dto.QuestionQueryDTO;
import com.ustc.community.model.Question;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question question);

	Integer countBySearch(QuestionQueryDTO questionQueryDTO);

	List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}