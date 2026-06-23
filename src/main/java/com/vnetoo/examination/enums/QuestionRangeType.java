package com.vnetoo.examination.enums;

public enum QuestionRangeType {
	
	SIMULATION,		//模拟题
	
	PRACTISE,		//练习
	
	TASK,			//作业
	
	EXAM,			//期末考试
	
	ENROL,			//入学考试
	
	COMMON;			//普通
	
	/**
	 * 根据字符串获取对应的枚举值
	 * @author Lisz
	 * @date  Apr 4, 2014
	 */
	public static QuestionRangeType getRangeType(String rangeType){
		if(rangeType == null || "".equals(rangeType.trim())){
			return null;
		}
		
		if("模拟题".equals(rangeType) || "SIMULATION".equalsIgnoreCase(rangeType)){
			return SIMULATION;
		}else if("练习".equals(rangeType) || "PRACTISE".equalsIgnoreCase(rangeType)){
			return PRACTISE;
		}else if("作业".equals(rangeType) || "TASK".equalsIgnoreCase(rangeType)){
			return TASK;
		}else if("期末考试".equals(rangeType) || "EXAM".equalsIgnoreCase(rangeType)){
			return EXAM;
		}else if("入学考试".equals(rangeType) || "ENROL".equalsIgnoreCase(rangeType)){
			return ENROL;
		}else if("普通".equals(rangeType) || "COMMON".equalsIgnoreCase(rangeType)){
			return COMMON;
		}else{
			return null;
		}
	}
}
