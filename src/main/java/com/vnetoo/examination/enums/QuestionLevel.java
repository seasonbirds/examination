/**
 * 
 */
package com.vnetoo.examination.enums;


/**
 * @author chenh
 * @date 2013年9月5日
 */
public enum QuestionLevel {
	EASY,
	MEDIUM,
	HARD;	
	
	public static QuestionLevel getLevel(String level){
		if(level == null){
			return null;
		}
		
		if("简单".equals(level) || "easy".equalsIgnoreCase(level)){
			return EASY;
		}else if("中等".equals(level) || "medium".equalsIgnoreCase(level)){
			return MEDIUM;
		}else if("难".equals(level) || "hard".equalsIgnoreCase(level)){
			return HARD;
		}else{
			return null;
		}
	}
}
