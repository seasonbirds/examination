package com.vnetoo.examination.policy.service;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;

/**
 * @comment 出题策略明细Service接口
 * @author chenh
 * @date 2013-11-13 
 */
public interface ExamPolicyDetailService extends BaseService<ExamPolicyDetail>{

	public Map<Integer, Integer> getQuestionNumberByPolicys(List<Integer> list);
}