package com.ustc.community.service;

import com.ustc.community.dto.Pagination;
import com.ustc.community.dto.QuestionDTO;
import com.ustc.community.exception.CustomizeErrorCode;
import com.ustc.community.exception.CustomizeException;
import com.ustc.community.mapper.QuestionExtMapper;
import com.ustc.community.mapper.QuestionMapper;
import com.ustc.community.mapper.UserMapper;
import com.ustc.community.model.Question;
import com.ustc.community.model.QuestionExample;
import com.ustc.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/27
 */
@Service
public class QuestionService {
	@Resource
	private QuestionMapper questionMapper;
	@Resource
	private QuestionExtMapper questionExtMapper;
	@Resource
	private UserMapper userMapper;

	public Pagination list(Integer page, Integer size){


		Pagination pagination=new Pagination();
		QuestionExample questionExample = new QuestionExample();
		Integer totalCount=(int)questionMapper.countByExample(questionExample);//计算数据总数
		Integer totalPage=(int)Math.ceil(totalCount*1.0/size);//页码

		//防止page参数越界
		if(page>totalPage){
			page=totalPage;
		}
		if(page<1){
			page=1;
		}

		pagination.setPagination(totalPage,page);

		Integer offset=size*(page-1);
		QuestionExample example = new QuestionExample();
		example.setOrderByClause("gmt_modified desc");
		List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset,size));
		List<QuestionDTO> questionDTOList = new ArrayList<>();

		for (Question question : questions) {
			User user=userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		pagination.setQuestions(questionDTOList);
//		System.out.println(questions.toString());

		return pagination;
	}

	public Pagination listByUserId(Long userId, Integer page, Integer size) {

		Pagination pagination=new Pagination();
		QuestionExample questionExample = new QuestionExample();
		questionExample.setOrderByClause("gmt_modified desc");
		questionExample.createCriteria()
				.andCreatorEqualTo(userId);

		Integer totalCount=(int)questionMapper.countByExample(questionExample);//计算数据总数
		Integer totalPage=(int)Math.ceil(totalCount*1.0/size);//页码

		//防止page参数越界
		if(page>totalPage){
			page=totalPage;
		}
		if(page<1){
			page=1;
		}

		pagination.setPagination(totalPage,page);

		Integer offset=size*(page-1);
		QuestionExample example = new QuestionExample();
		example.createCriteria()
				.andCreatorEqualTo(userId);
		List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example,new RowBounds(offset, size));
		List<QuestionDTO> questionDTOList = new ArrayList<>();

		for (Question question : questions) {
			User user=userMapper.selectByPrimaryKey(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		pagination.setQuestions(questionDTOList);
//		System.out.println(questions.toString());

		return pagination;
	}

	public QuestionDTO getById(Long id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if(question==null){
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		//把question的作者写入
		questionDTO.setUser(userMapper.selectByPrimaryKey(question.getCreator()));
		return questionDTO;

	}
//修改或者提出问题方法
	public void createOrUpdate(Question question) {
		if(question.getId()==null){
			//添加问题
			question.setCommentCount(0);
			question.setViewCount(0);
			question.setLikeCount(0);
			question.setGmtCreate(System.currentTimeMillis());
			questionMapper.insert(question);
		}else {
			QuestionExample example = new QuestionExample();
			example.createCriteria()
					.andIdEqualTo(question.getId());
			int updated = questionMapper.updateByExampleSelective(question, example);
			if(updated!=1){
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}

	public void incView(Long id) {
		Question question=new Question();
		question.setId(id);
		question.setViewCount(1);
		questionExtMapper.incView(question);
	}

	public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
		if(StringUtils.isBlank(queryDTO.getTag())){
			return new ArrayList<>();
		}
//		String[] tags = StringUtils.split(queryDTO.getTag(), "，");
		//替换成正则表达式
		String regexpTag = queryDTO.getTag().replace('，', '|');

		//查找所有的相关问题
		Question question = new Question();
		question.setId(queryDTO.getId());
		question.setTag(regexpTag);
		List<Question> questions = questionExtMapper.selectRelated(question);

		List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(q, questionDTO);
			return questionDTO;
		}).collect(Collectors.toList());

		return questionDTOS;
	}
}
