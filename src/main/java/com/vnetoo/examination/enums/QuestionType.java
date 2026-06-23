/**
 * 
 */
package com.vnetoo.examination.enums;


/**
 * @author chenh
 * @date 2013年9月2日
 */
public enum QuestionType {
	
	SINGLE,		//单选
	
	MULT,		//多选
	
	JUDGE,		//判断
	
	CLOZE,		//填空
	
	EXPLAIN,	//名词解释
		
	QNQ;		//问答题	
	
	public static QuestionType getQuestionType(String type){
		if(type == null){
			return null;
		}
		
		if("单选题".equals(type) || "single".equalsIgnoreCase(type)){
			return SINGLE;
		}else if("多选题".equals(type) || "mult".equalsIgnoreCase(type)){
			return MULT;
		}else if("判断题".equals(type) || "judge".equalsIgnoreCase(type)){
			return JUDGE;
		}else if("填空题".equals(type) || "cloze".equalsIgnoreCase(type)){
			return CLOZE;
		}else if("名词解释".equals(type) || "explain".equalsIgnoreCase(type)){
			return EXPLAIN;
		}else if("问答题".equals(type) || "qnq".equalsIgnoreCase(type)){
			return QNQ;
		}else
			return null;
	}
}
