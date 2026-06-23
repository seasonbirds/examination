package com.vnetoo.examination.policy.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.vnetoo.examination.enums.QuestionRangeType;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicy;

public class PaperFactory {
	private static final Logger log = Logger.getLogger(PaperFactory.class);
	
	//用户ID
	private Integer userId;
	//考试策略
	private ExamPolicy examPolicy; 
	//知识点列表
	private List<Integer> examKeyPointList;
	//考试类型
	private String rangeType;
	
	private String otherExamInfo;
	
	//考试类型对应的处理类
	private Map<String, PaperBuilder> builderHandlers;
	
	public PaperFactory(){

	}
	
	/**
	 * 工厂初始化方法
	 * @author Lisz
	 * @date  Apr 4, 2014
	 */
	public void init(Integer userId, ExamPolicy examPolicy, List<Integer> examKeyPointList, String rangeType, String otherExamInfo){
		this.userId = userId;
		this.examPolicy = examPolicy;
		this.examKeyPointList = examKeyPointList;
		this.otherExamInfo = otherExamInfo;
		this.rangeType = rangeType;
	}
	
	/**
	 * 
	 * @author Lisz
	 * @date  Apr 4, 2014
	 */
	public CreatePaperResult buildPaper(){
		PaperBuilder handler = null;
		if(builderHandlers.containsKey(rangeType)){
			handler = builderHandlers.get(rangeType);
		}else{
			QuestionRangeType qrt = QuestionRangeType.getRangeType(rangeType);
			if(qrt == null){
				log.error(String.format("%s type for exam is't support!",  rangeType));
				return new CreatePaperResult("PAPER.CREATE.NOSUPPORT_TYPE",null, null);
			}
			
			handler = builderHandlers.get("DEFAULT");
		}
		if(handler == null){
			log.error(String.format("Handler for %s or 'DEFAULT' type can't be null, someone must be exist.", rangeType));
			return new CreatePaperResult("PAPER.CREATE.FAILURE",null, null);
		}
		
		try {
			handler.init(userId, examPolicy, examKeyPointList, rangeType, otherExamInfo);
			return handler.build();
		} catch(Exception e){
			log.error("Paper failure to created.", e);
		}
		
		return new CreatePaperResult("PAPER.CREATE.FAILURE",null, null);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public ExamPolicy getExamPolicy() {
		return examPolicy;
	}

	public void setExamPolicy(ExamPolicy examPolicy) {
		this.examPolicy = examPolicy;
	}

	public List<Integer> getExamKeyPointList() {
		return examKeyPointList;
	}

	public void setExamKeyPointList(List<Integer> examKeyPointList) {
		this.examKeyPointList = examKeyPointList;
	}

	public String getRangeType() {
		return rangeType;
	}

	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}

	public String getOtherExamInfo() {
		return otherExamInfo;
	}

	public void setOtherExamInfo(String otherExamInfo) {
		this.otherExamInfo = otherExamInfo;
	}

	public Map<String, PaperBuilder> getBuilderHandlers() {
		return builderHandlers;
	}

	public void setBuilderHandlers(Map<String, PaperBuilder> builderHandlers) {
		this.builderHandlers = builderHandlers;
	}
}
