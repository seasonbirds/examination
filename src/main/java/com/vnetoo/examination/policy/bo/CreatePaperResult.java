package com.vnetoo.examination.policy.bo;

/**
 * 创建试卷结果信息类
 */
public class CreatePaperResult {
	//结果代码
	private String code;
	//题目类型
	private String type;
	//题目难易度
	private String lev;

	public CreatePaperResult(){
		
	}
	
	public CreatePaperResult(String code, String type, String lev){
		this.code = code;
		this.type = type;
		this.lev = lev;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLev() {
		return lev;
	}

	public void setLev(String lev) {
		this.lev = lev;
	}
	
}
