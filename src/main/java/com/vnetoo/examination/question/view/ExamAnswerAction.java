package com.vnetoo.examination.question.view;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.service.ExamAnswerService;

/**
 * @comment 答案表Action
 * @author chenh
 * @date 2013-11-08 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="examAnswer",
results=
{
		@Result(name="list",	type="freemarker",location="examAnswer/list.htm"),
		@Result(name="data",	type="freemarker",location="examAnswer/data.htm"),	
		@Result(name="create",	type="freemarker",location="examAnswer/create.htm"),
}
)
public class ExamAnswerAction extends BaseAction<ExamAnswer>{
	@Resource
	private ExamAnswerService examAnswerService;
	
	private ExamAnswer examAnswer = new ExamAnswer();
	
	public void setExamAnswerService(ExamAnswerService examAnswerService) {
		this.examAnswerService = examAnswerService;
	}
	
	public ExamAnswerService getExamAnswerService() {
		return examAnswerService;
	}
	
	public void setExamAnswer(ExamAnswer examAnswer) {
		this.examAnswer = examAnswer;
	}
	
	public ExamAnswer getExamAnswer() {
		return examAnswer;
	}
	
	/**************************************************************************************/
		
	/**
	 * 进入查询答案表页面
	 * /jsp/examAnswer/list.htm
	 */
	public String list() 
	{		
		search();
		return RESULT_LIST;
	}
		
	/**
	 * 查询答案表
	 * /jsp/examAnswer/data.jsp
	 */
	public String search() 
	{				
		examAnswerService.pageQuery(examAnswer,paginator);
		return RESULT_DATA;
	}
	
	/**
	 * 进入答案表修改页面
	 * /jsp/examAnswer/CreateExamAnswer.jsp 
	 */
	public String toCreateOrUpdate()
	{
		if(examAnswer.getId()!=null&&examAnswer.getId().longValue()>0){//修改
			examAnswer=examAnswerService.findById(examAnswer.getId());			
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存答案表	
	 */
	public String save()
	{
		if(examAnswer.getId()!=null&&examAnswer.getId().longValue()>0){
			ExamAnswer po=examAnswerService.findById(examAnswer.getId());			
			//po.setName(examAnswer.getName());
			
			//po.setUpdateBy(new Integer(0));			
			//examAnswer=po;
		}
		else{
			//examAnswer.setCreateBy(new Integer(0));
			//examAnswer.setUpdateBy(examAnswer.getCreateBy());
		}
		try{
			examAnswerService.saveOrUpdate(examAnswer);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除答案表
	 */
	public String remove() 
	{
		try{
			examAnswerService.removeLogic(examAnswer.getId());			
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除答案表
	 */
	public String batchRemove() 
	{
		try{
			for(int i=0;i<examAnswer.getIds().length;i++){
				examAnswerService.removeLogic(examAnswer.getIds()[i]);		
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	/**************************************************************************************/
}



