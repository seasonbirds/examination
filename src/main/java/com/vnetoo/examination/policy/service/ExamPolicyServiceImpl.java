package com.vnetoo.examination.policy.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.vnetoo.common.enums.Bool;
import com.vnetoo.common.service.BaseServiceImpl;

import org.springframework.stereotype.Service;

import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.bo.ScopePolicyRel;
import com.vnetoo.examination.app.dao.AppCourseDAO;
import com.vnetoo.examination.app.dao.AppScopeDAO;
import com.vnetoo.examination.app.dao.ScopeKeyPointRelDAO;
import com.vnetoo.examination.app.dao.ScopePolicyRelDAO;
import com.vnetoo.examination.enums.QuestionLevel;
import com.vnetoo.examination.enums.QuestionType;
import com.vnetoo.examination.keypoint.dao.ExamKeyPointDAO;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.dao.ExamPaperDAO;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;
import com.vnetoo.examination.policy.dao.ExamPolicyDAO;
import com.vnetoo.examination.policy.dao.ExamPolicyDetailDAO;

/**
 * @comment 出题策略Service实现类
 * @author chenh
 * @date 2013-11-13 
 */
@Service 
public class ExamPolicyServiceImpl extends BaseServiceImpl<ExamPolicy> implements ExamPolicyService{
	@Resource
	private ExamPolicyDetailDAO examPolicyDetailDAO;
	@Resource
	private ScopePolicyRelDAO scopePolicyRelDAO;
	@Resource
	private AppCourseDAO appCourseDAO;
	@Resource
	private ScopeKeyPointRelDAO scopeKeyPointRelDAO;
	@Resource
	private ExamKeyPointDAO examKeyPointDAO;
	@Resource
	private ExamPaperDAO examPaperDAO;
	@Resource
	private AppScopeDAO appScopeDAO;
	@Resource
	private PaperFactory paperFactory;
	
	public ExamPolicyDAO getDao() {
		return (ExamPolicyDAO)super.dao;
	}
	
	@Resource(name="examPolicyDAOImpl")
	public void setDao(ExamPolicyDAO dao) {
		super.dao = dao;
	}
	
	public Integer savePolicy(ExamPolicy examPolicy, String[] policyDetail, Integer userId){
		if(examPolicy == null || policyDetail == null || userId == null){
			return null;
		}
		
		Integer policyId = 0;
		if(examPolicy.getId() == null || examPolicy.getId() == 0){
			examPolicy.setCreatedBy(userId);
			policyId = dao.insert(examPolicy);
			if(policyId == null || policyId < 1){
				return null;
			}
			ScopePolicyRel scopePolicyRel = new ScopePolicyRel();
			scopePolicyRel.setScopeId(examPolicy.getScopeId());
			scopePolicyRel.setPolicyId(policyId);
			if(scopePolicyRelDAO.insert(scopePolicyRel) <= 0){
				return null;
			}
		}else{
			examPolicy.setLastUpdatedBy(userId);
			dao.update(examPolicy);
			policyId = examPolicy.getId();
			//更新时先删除策略详情
			examPolicyDetailDAO.deleteByPolicyId(policyId);
		}
		
		for(String detail : policyDetail){
			String[] details = detail.split(",");
			ExamPolicyDetail epd = new ExamPolicyDetail();
			epd.setExamPolicyId(policyId);
			epd.setType(QuestionType.getQuestionType(details[0]));
			epd.setLev(QuestionLevel.getLevel(details[1]));
			epd.setQuestionCount(Integer.parseInt(details[2]));
			epd.setPerScore(Integer.parseInt(details[3]));
			epd.setCreatedBy(userId);
			Integer detailId = examPolicyDetailDAO.insert(epd);
			if(detailId != null && detailId <= 0){
				return null;
			}
		}
		return policyId;
	}
	
	public void deletePolicyDetailByPolicy(Integer policyId){
		examPolicyDetailDAO.deleteByPolicyId(policyId);
	}
	
	public List<ExamPolicy> getPolicyWithAutoPaper(){
		return getDao().getPolicyWithAutoPaper();
	}
	
	public CreatePaperResult createPaper(Integer policyId, Integer userId, String otherExamInfo){
		//获取策略
		ExamPolicy examPolicy = dao.selectOne(policyId);
		if(examPolicy == null){
			return new CreatePaperResult("PAPER.CREATE.NOPOLICY", null, null);
		}
		
		//获取考试范围
		Integer scopeId = scopePolicyRelDAO.getScopeIdByPolicyId(policyId);
		if(scopeId == null || scopeId <= 0){
			return new CreatePaperResult("PAPER.CREATE.NOSCOPE", null, null);
		}
		AppScope appScope = appScopeDAO.selectOne(scopeId);
		if(appScope == null){
			return new CreatePaperResult("PAPER.CREATE.NOSCOPE", null, null);
		}
		
		//获取考试范围对应的课程
		List<Integer> appCourseList = appCourseDAO.getCourseIdListByScopeId(scopeId);
		if(appCourseList == null || appCourseList.size() < 1){
			return new CreatePaperResult("PAPER.CREATE.NOCOURSE", null, null);
		}
		
		//考试对应的知识点
		List<Integer> examKeyPointList = new ArrayList<Integer>();
		//考试范围只有一门课程且设置了知识点
		if(appCourseList.size() == 1){
			examKeyPointList = scopeKeyPointRelDAO.getKeyPointIdListByScopeId(scopeId);
		}
		//考试范围有多门课程或者只有一门但没有设置知识点，获取课程对应的所有知识点
		if(examKeyPointList == null || examKeyPointList.size() == 0){
			examKeyPointList = examKeyPointDAO.getKeyPointIdListByCourseId(appCourseList);
		}
		//无知识点
		if(examKeyPointList == null || examKeyPointList.size() == 0){
			return new CreatePaperResult("PAPER.CREATE.NOKEYPOINT", null, null);
		}
		
		//获取策略原有试卷,根据试卷生成的结果进行更新
		ExamPaper examPaper = new ExamPaper();
		examPaper.setExamPolicyId(policyId);
		List<ExamPaper> papers = examPaperDAO.selectList(examPaper);
		
		/*PaperFactory factory = new PaperFactory(userId, examPolicy, examKeyPointList, appScope.getType().toString(), otherExamInfo);
		PaperBuilder builder = factory.getBuilder();
		CreatePaperResult result = builder.build();*/
		paperFactory.init(userId, examPolicy, examKeyPointList, appScope.getType().toString(), otherExamInfo);
		CreatePaperResult result = paperFactory.buildPaper();
		
		if("PAPER.CREATE.SUCCESS".equals(result.getCode())){
			//将被使用的试卷置为过期，未被使用的试卷删除
			if(papers != null && papers.size() > 0){
				for(ExamPaper paper : papers){
					if(paper.getUsed().equals(Bool.YES)){
						paper.setStatus(Bool.NO);
					}else {
						paper.setEnabledFlag(0);
					}
					paper.setUpdatedBy(userId);
					examPaperDAO.update(paper);
				}
			}
		}
		
		return result;
	}
	
	public List<ExamPolicy> getPolicyByScopeIds(List<Integer> policyIds){
		return getDao().getPolicyByScopeIds(policyIds);
	}
	
	public ExamPolicy getPolicyByPaperGuid(String guid){
		return getDao().getPolicyByPaperGuid(guid);
	}
}
