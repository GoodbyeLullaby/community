package com.ustc.community.dto;

import com.ustc.community.model.Question;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: GoodbyeLullaby
 * @Date: 2020/2/28
 */
@Data
public class Pagination<T> {
	private List<T> data;
	private boolean showPrevious;
	private boolean showFirstPage;
	private boolean showNext;
	private boolean showEndPage;
	private Integer page;
	private Integer totalPage;
	private List<Integer> pages=new ArrayList<>();

	/**
	 *
	 * @param totalPage
	 * @param page
	 */
	public void setPagination(Integer totalPage, Integer page) {
		this.totalPage=totalPage;
		this.page=page;

		pages.add(page);
		for(int i=1;i<=3;i++){
			if(page-i>0){
				//从0的位置加
				pages.add(0,page-i);
			}
			if(page+i<=totalPage){
				pages.add(page+i);
			}
		}


		//是否展示前一页
		if(page==1){
			showPrevious=false;
		}else {
			showPrevious=true;
		}


		//是否展示后一页
		if(page==totalPage){
			showNext=false;
		}else {
			showNext=true;
		}


		//是否展示第一页
		if(pages.contains(1)){
			showFirstPage=false;
		}else {
			showFirstPage=true;
		}


		//是否展示最后一页
		if(pages.contains(totalPage)){
			showEndPage=false;
		}else{
			showEndPage=true;
		}

	}
}
