package com.vnetoo.examination.app.service;

import java.util.List;

import javax.annotation.Resource;

import com.vnetoo.common.service.BaseServiceImpl;

import org.springframework.stereotype.Service;

import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.bo.ScopeCourseRel;
import com.vnetoo.examination.app.bo.ScopeKeyPointRel;
import com.vnetoo.examination.app.dao.AppScopeDAO;
import com.vnetoo.examination.app.dao.ScopeCourseRelDAO;
import com.vnetoo.examination.app.dao.ScopeKeyPointRelDAO;
import com.vnetoo.examination.app.dao.ScopePolicyRelDAO;
import com.vnetoo.examination.paper.dao.ExamPaperDAO;
import com.vnetoo.examination.policy.dao.ExamPolicyDAO;
import com.vnetoo.examination.policy.dao.ExamPolicyDetailDAO;

/**
 * @comment Service实现类
 * @author chenh
 * @date 2013-11-11 
 */
@SuppressWarnings("restriction")
@Service 
public class AppScopeServiceImpl extends BaseServiceImpl<AppScope> implements AppScopeService{
	
	public AppScopeDAO getDao() {
		// TODO Auto-generated method stub
		return (AppScopeDAO)super.dao;
	}
	
	@Resource(name="appScopeDAOImpl")
	public void setDao(AppScopeDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
	@Resource
	private ExamPaperDAO examPaperDAO;
	@Resource
	private ScopePolicyRelDAO scopePolicyRelDAO;
	@Resource
	private ExamPolicyDAO examPolicyDAO;
	@Resource
	private ExamPolicyDetailDAO examPolicyDetailDAO;
	@Resource
	private ScopeKeyPointRelDAO scopeKeyPointRelDAO;
	@Resource
	private ScopeCourseRelDAO scopeCourseRelDAO;
	
	public void delete(Integer scopeId) {
		//删除考试相关试卷
		List<Integer> paperIds = examPaperDAO.getPaperIdsByScopeId(scopeId);
		if(paperIds != null && !paperIds.isEmpty()){
			for(Integer paperId : paperIds){
				examPaperDAO.deleteLogic(paperId);
			}
		}
		
		//删除考试相关策略
		List<Integer> policyIds = scopePolicyRelDAO.getPolicyIds(scopeId);
		if(policyIds != null && !policyIds.isEmpty()){
			for(Integer policyId : policyIds){
				//删除策略详情
				examPolicyDetailDAO.deleteByPolicyId(policyId);
				//删除策略
				examPolicyDAO.deleteLogic(policyId);
				//删除scope-policy的关系
				scopePolicyRelDAO.deleteRel(policyId);
			}
		}
		
		//删除考试相关知识点
		List<Integer> keyPointIds = scopeKeyPointRelDAO.getKeyPointIdListByScopeId(scopeId);
		if(keyPointIds != null && !keyPointIds.isEmpty()){
			scopeKeyPointRelDAO.deleteRel(scopeId);
		}
		
		//删除考试相关课程
		scopeCourseRelDAO.deleteRel(scopeId);
		
		//删除考试记录
		getDao().deleteLogic(scopeId);
	}
	
	public void save(AppScope appScope, Boolean courseModFlag, String courseIds, Boolean keyPointModFlag, String keyPointIds){
		//插入或者更新考试信息
		Integer id = saveOrUpdate(appScope);
		
		//插入或者更新课程信息
		if(courseModFlag != null && courseModFlag == true){
			//先删除原有关系
			scopeCourseRelDAO.deleteRel(id);
			//插入新的关系
			String[] courseIdArray = courseIds.split(",");
			for(String cid : courseIdArray){
				ScopeCourseRel scr = new ScopeCourseRel();
				scr.setScopeId(id);
				scr.setCourseId(Integer.parseInt(cid));
				scopeCourseRelDAO.insert(scr);
			}
		}
		
		//插入或者更新知识点信息
		if(keyPointModFlag != null && keyPointModFlag == true){
			//先删除原有关系
			scopeKeyPointRelDAO.deleteRel(id);
			//插入新的关系
			//知识点可能为空(取消设置知识点)，课程至少有一门
			if(keyPointIds != null && !"".equals(keyPointIds.trim())){
				String[] keyPointIdArray = keyPointIds.split(",");
				for(String kid : keyPointIdArray){
					ScopeKeyPointRel skpr = new ScopeKeyPointRel();
					skpr.setScopeId(id);
					skpr.setKeyPointId(Integer.parseInt(kid));
					scopeKeyPointRelDAO.insert(skpr);
				}
			}
		}
	}
	
	public List<AppScope> getAppScopeByIds(Integer[] array){
		return getDao().getAppScopeByIds(array);
	}
	
	public List<AppScope> getAppScopeByTypeAndCourse(String type, Integer courseId){
		return getDao().getAppScopeByTypeAndCourse(type, courseId);
	}
}
