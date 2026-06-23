package com.vnetoo.examination.policy.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicy;

/**
 * @comment 出题策略Service接口
 * @author chenh
 * @date 2013-11-13 
 */
public interface ExamPolicyService extends BaseService<ExamPolicy>{

	/**
	 * 保存策略
	 * @author Lisz
	 * @date  2014-1-8  上午10:13:36
	 */
	public Integer savePolicy(ExamPolicy examPolicy, String[] policyDetail, Integer userId);
	 
	public void deletePolicyDetailByPolicy(Integer policyId);
	
	/**
	 * 创建试卷
	 * @author Lisz
	 * @date  2014-1-8  上午10:08:59
	 */
	public CreatePaperResult createPaper(Integer policyId, Integer userId, String otherExamInfo);
	
	public List<ExamPolicy> getPolicyWithAutoPaper();
	
	/**
	 * 根据考试ID集合查找策略
	 */
	public List<ExamPolicy> getPolicyByScopeIds(List<Integer> policyIds);
	
	/**
	 * 根据试卷的GUID查找对应的策略
	 * @param guid
	 * @return
	 */
	public ExamPolicy getPolicyByPaperGuid(String guid);
}