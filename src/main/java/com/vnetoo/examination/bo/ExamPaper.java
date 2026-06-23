package com.vnetoo.examination.bo;

import java.util.ArrayList;
import java.util.List;

import com.vnetoo.examination.question.bo.ExamQuestion;


/**
 * @comment 试卷表
 * @author Administrator
 * @date 2012-06-01
 */
@SuppressWarnings("serial")
public class ExamPaper extends AbstractExamPaper implements
		java.io.Serializable {
	

	private List<ExamQuestion> questionList = new ArrayList<ExamQuestion>();

	public List<ExamQuestion> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<ExamQuestion> questionList) {
		this.questionList = questionList;
	}
}