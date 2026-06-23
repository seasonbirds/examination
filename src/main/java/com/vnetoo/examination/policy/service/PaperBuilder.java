package com.vnetoo.examination.policy.service;

import java.util.List;

import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicy;

public interface PaperBuilder {

	public CreatePaperResult build();
	
	public void init(Integer userId, ExamPolicy examPolicy, List<Integer> examKeyPointList, String rangeType, String otherExamInfo);
}
