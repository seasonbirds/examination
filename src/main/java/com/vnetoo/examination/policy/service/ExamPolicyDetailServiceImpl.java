package com.vnetoo.examination.policy.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;
import com.vnetoo.examination.policy.dao.ExamPolicyDetailDAO;

/**
 * @comment 出题策略明细Service实现类
 * @author chenh
 * @date 2013-11-13 
 */
@Service 
public class ExamPolicyDetailServiceImpl extends BaseServiceImpl<ExamPolicyDetail> implements ExamPolicyDetailService{
	
	public ExamPolicyDetailDAO getDao() {
		// TODO Auto-generated method stub
		return (ExamPolicyDetailDAO)super.dao;
	}
	
	@Resource(name="examPolicyDetailDAOImpl")
	public void setDao(ExamPolicyDetailDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
	
	public Map<Integer, Integer> getQuestionNumberByPolicys(List<Integer> list){
		return getDao().getQuestionNumberByPolicys(list);
	}
}
