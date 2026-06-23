package com.vnetoo.examination.policy.view;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.view.BaseAction;
import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;
import com.vnetoo.examination.policy.service.ExamPolicyDetailService;

import freemarker.ext.beans.ResourceBundleModel;

/**
 * @comment 出题策略明细Action
 * @author chenh
 * @date 2013-11-13 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="examPolicyDetail",
results=
{
		@Result(name="list",	type="freemarker",location="examPolicyDetail/list.htm"),
		@Result(name="data",	type="freemarker",location="examPolicyDetail/data.htm"),	
		@Result(name="create",	type="freemarker",location="examPolicyDetail/create.htm"),
}
)
public class ExamPolicyDetailAction extends BaseAction<ExamPolicyDetail>{
	@Resource
	private ExamPolicyDetailService examPolicyDetailService;
	
	private ExamPolicyDetail examPolicyDetail = new ExamPolicyDetail();
	
	public void setExamPolicyDetailService(ExamPolicyDetailService examPolicyDetailService) {
		this.examPolicyDetailService = examPolicyDetailService;
	}
	
	public ExamPolicyDetailService getExamPolicyDetailService() {
		return examPolicyDetailService;
	}
	
	public void setExamPolicyDetail(ExamPolicyDetail examPolicyDetail) {
		this.examPolicyDetail = examPolicyDetail;
	}
	
	public ExamPolicyDetail getExamPolicyDetail() {
		return examPolicyDetail;
	}
	
	/**************************************************************************************/
		
	/**
	 * 进入查询出题策略明细页面
	 * /jsp/examPolicyDetail/list.htm
	 */
	public String list() 
	{		
		search();
		return RESULT_LIST;
	}
		
	/**
	 * 查询出题策略明细
	 * /jsp/examPolicyDetail/data.jsp
	 */
	public String search() 
	{				
		examPolicyDetailService.pageQuery(examPolicyDetail,paginator);
		return RESULT_DATA;
	}
	
	/**
	 * 进入出题策略明细修改页面
	 * /jsp/examPolicyDetail/CreateExamPolicyDetail.jsp 
	 */
	public String toCreateOrUpdate()
	{
		if(examPolicyDetail.getId()!=null&&examPolicyDetail.getId().longValue()>0){//修改
			examPolicyDetail=examPolicyDetailService.findById(examPolicyDetail.getId());			
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存出题策略明细	
	 */
	public String save()
	{
		if(examPolicyDetail.getId()!=null&&examPolicyDetail.getId().longValue()>0){
			ExamPolicyDetail po=examPolicyDetailService.findById(examPolicyDetail.getId());			
			//po.setName(examPolicyDetail.getName());
			
			//po.setUpdateBy(new Integer(0));			
			//examPolicyDetail=po;
		}
		else{
			//examPolicyDetail.setCreateBy(new Integer(0));
			//examPolicyDetail.setUpdateBy(examPolicyDetail.getCreateBy());
		}
		try{
			examPolicyDetailService.saveOrUpdate(examPolicyDetail);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除出题策略明细
	 */
	public String remove() 
	{
		try{
			examPolicyDetailService.removeLogic(examPolicyDetail.getId());			
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除出题策略明细
	 */
	public String batchRemove() 
	{
		try{
			for(int i=0;i<examPolicyDetail.getIds().length;i++){
				examPolicyDetailService.removeLogic(examPolicyDetail.getIds()[i]);		
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	public String getPolicyDetails(){
		JSONObject json = new JSONObject();
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			JSONArray a = new JSONArray();
			List<ExamPolicyDetail>l = examPolicyDetailService.dynamicQuery(examPolicyDetail);
			if(l != null && l.size() > 0){
				for(ExamPolicyDetail detail: l){
					JSONObject item = new JSONObject();
					item.put("type", detail.getType());
					item.put("level", detail.getLev());
					item.put("questionCount", detail.getQuestionCount());
					item.put("perScore", detail.getPerScore());
					a.add(item);
				}
			}
			json.put("result", "success");
			json.put("data", a);
		}catch(Exception e){
			json.put("result", "failure");
			json.put("data", rbm.format("PARAMETER.ERROR", null));
		}
		
		try {
			this.writeResponse(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**************************************************************************************/
}



