/**
 * 
 */
package com.vnetoo.examination.action;

import org.apache.log4j.Logger;

import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;

/**
 * @author chenh
 * @date 2013年8月7日
 */
@SuppressWarnings("serial")
public class ExamMgrAction extends BaseAction<ExamPolicy>{
	Logger logger = Logger.getLogger(ExamMgrAction.class);
	private int examScopeId;
	private ExamPolicy examPolicy;
	private ExamPolicyService examPolicyService;
	
	public ExamPolicyService getExamPolicyService() {
		return examPolicyService;
	}

	public void setExamPolicyService(ExamPolicyService examPolicyService) {
		this.examPolicyService = examPolicyService;
	}

	public int getExamScopeId() {
		return examScopeId;
	}

	public void setExamScopeId(int examScopeId) {
		this.examScopeId = examScopeId;
	}
	
	public ExamPolicy getExamPolicy() {
		return examPolicy;
	}

	public void setExamPolicy(ExamPolicy examPolicy) {
		this.examPolicy = examPolicy;
	}

	/**
	 * 设置考试策略
	 * @author chenh
	 * @date Jun 27, 2013
	 */
	public String addExamPolicy(){
		
		logger.info("设置考试策略");
		examPolicy = examPolicyService.findById(1);
		if(examPolicy==null)
			examPolicy = new ExamPolicy();
		
		//examPolicy.setExamScopeId(examScopeId);
		return "ok";
	}
	
	/**
	 * 保存考试策略
	 * @author chenh
	 * @date Jun 27, 2013
	 */
	public void saveExamPolicy(){
		logger.error("保存考试策略");		
		//判断题目数量够不够生成试卷
		int result = checkQuestionCount(examPolicy);
		if(result>0){
			this.responseFlag = result;
			this.writeResponse();
			return;
		}
		
		//保存策略和明细	
		if(examPolicy.getId()==null || examPolicy.getId().intValue()==0){	
			//examPolicy.setCreatedBy();
		}		
		//examPolicy.setLastUpdatedBy(examEvent.getCurrentUserId());
		
		//保存策略和考试范围关系
		int policyId = 0;//examPolicyService.saveExamPolicy(examPolicy);	
		System.out.println(policyId);
		/*examEvent.savePolicyScope(examScopeId,policyId);
		
		//之前的试卷修改为过期状态或者作废
		examMgrDao.updateExamPageStatus(policyId);
		
		//重新生成试卷记录并生成静态页面
		if(examPolicy.getPaperCount()!=null && examPolicy.getPaperCount().intValue()>0){
			for (int i = 0; i < examPolicy.getPaperCount().intValue(); i++) {
				ExamPaper ePaper = new ExamPaper();
				ePaper.setStatus(1);
				ePaper.setUsed(0);
				ePaper.setPaperUrl(ConfigRepository.getValue(ExamConstant.KEY_EXAM_PAPER_PATH)+"/"+policyId+"/"+examScopeId);
				ePaper.setExamPolicyId(policyId);
				ePaper.setEnabledFlag(1);
				ePaper.setCreatedBy(examEvent.getCurrentUserId());						
				ePaper.setTime(examPolicy.getTime());
				ePaper.setTotalScore(examPolicy.getTotalScore());
				ePaper.setPassScore(examPolicy.getPassScore());
				int paperId = DataAccessDriver.getInstance().saveExamPaper(ePaper);
				try {
					if(!makeToHtml(ConfigRepository.getValue(ExamConstant.KEY_SERVLET_URI)+"/exam?module=examMgr&action=createPaper&policyId="+policyId+"&examScopeId=1000"+"&paperId="+paperId,
							ConfigRepository.getValue(ExamConstant.KEY_APP_PATH)+ePaper.getPaperUrl(),
							DataAccessDriver.getInstance().getExamPaper(paperId)+".htm"))
						this.responseFlag = -1;						
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}*/
		
		this.writeResponse();
	}
	
	/**
	 * 判断题目数量够不够生成试卷
	 * @author chenh
	 * @date Jun 27, 2013
	 * @return
	 */
	private int checkQuestionCount(ExamPolicy policy){
		/*int examScopeId = policy.getExamScopeId();
		//选单
		policy.getDetail_single().setType(ExamConstant.EXAM_TYPE_MONOMIAL);
		int monomialCount = policy.getDetail_single().getQuestionCount().intValue();
		int monomialLev = policy.getDetail_single().getLev().intValue();
		if(monomialCount>0){
			int count = examPolicyService.findExamQuestionCount(examScopeId, monomialLev, ExamConstant.EXAM_TYPE_MONOMIAL);
			if(monomialCount>count){
				responseMsg = "单选试题量不够，需要"+monomialCount+"个，题库只有"+count+"个。";
				logger.warn(responseMsg);
				return ExamConstant.EXAM_TYPE_MONOMIAL;
			}
		}		
		//多选
		policy.getDetail_mulsel().setType(ExamConstant.EXAM_TYPE_MORE);
		int moreCount = policy.getDetail_mulsel().getQuestionCount().intValue();
		int moreLev = policy.getDetail_mulsel().getLev().intValue();
		if(moreCount>0){
			int count = examPolicyService.findExamQuestionCount(examScopeId, moreLev, ExamConstant.EXAM_TYPE_MORE);
			if(moreCount>count){
				responseMsg = "单选试题量不够，需要"+moreCount+"个，题库只有"+count+"个。";
				logger.warn(responseMsg);
				return ExamConstant.EXAM_TYPE_MORE;
			}				
		}
		//判断
		policy.getDetail_option().setType(ExamConstant.EXAM_TYPE_OPIN);
		int opinCount = policy.getDetail_option().getQuestionCount().intValue();
		int opinLev = policy.getDetail_option().getLev().intValue();
		if(opinCount>0){
			int count = examPolicyService.findExamQuestionCount(examScopeId, opinLev, ExamConstant.EXAM_TYPE_OPIN);
			if(opinCount>count){
				responseMsg = "单选试题量不够，需要"+opinCount+"个，题库只有"+count+"个。";
				logger.warn(responseMsg);
				return ExamConstant.EXAM_TYPE_OPIN;
			}
		}*/
		return 0;
	}
}
