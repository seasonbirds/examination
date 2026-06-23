package com.vnetoo.examination.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.vnetoo.common.enums.Bool;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.PaperQuestionRel;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;

public class EnrolPaperBuilder extends AbstractPaperBuilder {
	
	public CreatePaperResult build() {
		if(userId == null || examPolicy == null || examKeyPointList == null 
				|| examKeyPointList.isEmpty() || otherExamInfo == null || "".equals(otherExamInfo.trim())){
			return new CreatePaperResult("SERVER.EXCEPTION", null, null);
		}
		
		Integer policyId = examPolicy.getId();
		//获取策略详情
		ExamPolicyDetail examPolicyDetail = new ExamPolicyDetail();
		examPolicyDetail.setExamPolicyId(policyId);
		List<ExamPolicyDetail> details = examPolicyDetailDAO.selectList(examPolicyDetail);
		if(details == null || details.isEmpty()){
			return new CreatePaperResult("PAPER.CREATE.DETAILNULL", null, null);
		}
		
		//计算本考试总题量
		int totalQuestionNumber = 0;
		for(ExamPolicyDetail detail : details){
			totalQuestionNumber += detail.getQuestionCount();
		}
		
		String[] idRatio = otherExamInfo.split(",");
		//子题库对应的考试Id
		Integer eId = Integer.parseInt(idRatio[0]);
		//计算子题库所占题量
		int subNumber = (int) Math.round(Integer.parseInt(idRatio[1]) / 100.0 * totalQuestionNumber);
		
		//根据考试Id查找对应的试卷Id
		List<Integer> paperIds = examPaperDAO.getPaperIdsByScopeId(eId);
		if(paperIds == null || paperIds.isEmpty()){
			return new CreatePaperResult("SERVER.EXCEPTION", null, null);
		}
		//初步判断子题库数量是否满足所占数量
		Integer number = paperQuestionRelDAO.getQuestionNumberByPaperIds(paperIds);
		if(number < subNumber){
			return new CreatePaperResult("PAPER.CREATE.CHILDEXAM.NOTENOUGH", null, null);
		}
		
		//判断总题库中除了各子题库后剩余题量是否满足要求
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("rangeType", "SIMULATION");
		params.put("list", examKeyPointList);
		//总题库数量
		Integer qtNumber = examQuestionDAO.getQuestionCount(params);
		//总题库数量 - 子题库数量 > 本考试总题量 - 子题库所占题量
		if(qtNumber - number < totalQuestionNumber - subNumber){
			return new CreatePaperResult("PAPER.CREATE.CHILDEXAM.NOTENOUGH", null, null);
		}
		
		//获取子题库试题，按类型和id对应关系存储
		Map<String, List<Integer>> sqMap = paperQuestionRelDAO.getQuestionTypeAndIdsByPaperId(paperIds);
		//子题库所有题目Id
		List<Integer> sIdList = new ArrayList<Integer>();
		Set<String> sqKeySet = sqMap.keySet();
		for(Iterator<String> it = sqKeySet.iterator(); it.hasNext(); ){
			sIdList.addAll(sqMap.get(it.next()));
		}
		
		//获取总题库中除了子题库内的试题,按类型和id对应关系存储
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("rangeType", "SIMULATION");
		paraMap.put("keyPoint", examKeyPointList);
		paraMap.put("excludeIds", sIdList);
		Map<String, List<Integer>> tqMap = examQuestionDAO.getQuestionTypeAndIds(paraMap);
		
		//题目分配情况表：各类型题目和分配数量对应关系
		Map<String, Integer> typeNumStatus = new HashMap<String, Integer>();
		for(ExamPolicyDetail detail : details){
			String type = detail.getType().toString()+ detail.getLev();
			typeNumStatus.put(type, 0);
		}
		
		Random random = new Random();
		//根据试卷份数产生试卷
		int paperCount = examPolicy.getPaperCount();
		for(int i=0; i<paperCount; i++){
			//题目分配信息表
			Map<String, Integer> typeNumInfo = new HashMap<String, Integer>();
			//题目分配表
			Map<String, List<Integer>> typeIds = new HashMap<String, List<Integer>>();
			for(ExamPolicyDetail detail : details){
				String type = detail.getType().toString()+ detail.getLev();
				typeNumInfo.put(type, detail.getQuestionCount());
				typeIds.put(type , new ArrayList<Integer>());
			}
			//分配子题库中的题目
			//每份试卷的分配会对子题库的Map修改，为保存原子题库，保存一份子题库的copy
			Map<String, List<Integer>> sqMapClone = depthCopy(sqMap);
			//循环产生子题库中的题目
			for(int n=0; n< subNumber; n++){
				Set<String> keySet = sqMapClone.keySet();
				if(keySet == null || keySet.isEmpty()){
					return new CreatePaperResult("PAPER.CREATE.CHILDEXAM.NOTENOUGH", null, null);
				}
				//先对试题的类型随机获取，再对试题随机获取
				List<String> keyList = new ArrayList<String>(sqMapClone.keySet());
				String type = keyList.get(random.nextInt(keyList.size()));
				//该题型的题量已经满足用户的设置量：
				//1、子题库中删除该题型的试题，以免下次随机时再次随机到该类型
				//2、本次取题不成功，循环数减一，保证循环的总数，
				Integer numStatus = typeNumStatus.get(type);
				if(numStatus >= typeNumInfo.get(type)){
					sqMapClone.remove(type);
					n--;
					continue;
				}else{
					//在该题型的试题上随机获取试题
					List<Integer> sqList = sqMapClone.get(type);
					//该题型试题为空:
					//1、删除该题型
					//2、本次取题不成功，循环数减一，保证循环的总数，
					if(sqList == null || sqList.isEmpty()){
						sqMapClone.remove(type);
						n--;
						continue;
					}else{
						Integer qId = sqList.get(random.nextInt(sqList.size()));
						//该题型获取试题数+1
						numStatus++;
						typeNumStatus.put(type, numStatus);
						//将试题加入分配信息的该题型列表中
						typeIds.get(type).add(qId);
						//子题库该题型试题类别删除该试题，以免重复获取
						sqList.remove(qId);
					}
				}
			}
			
			//分配总题库的题目
			Set<String> keyStatus = typeNumStatus.keySet();
			for(Iterator<String> it = keyStatus.iterator(); it.hasNext(); ){
				String type = it.next();
				int remainNumber = typeNumInfo.get(type) - typeNumStatus.get(type);
				//某种类型的题目在子题库分配时未分配完成
				if(remainNumber > 0){
					List<Integer> idList = typeIds.get(type);
					List<Integer> qIdList = depthCopyList(tqMap.get(type));
					if(qIdList.size() < remainNumber){
						return new CreatePaperResult("PAPER.CREATE.TOTALEXAM.NOTENOUGH", null, null);
					}
					for(int m = 0; m < remainNumber; m++){
						Integer qId = qIdList.get(random.nextInt(qIdList.size()));
						idList.add(qId);
						qIdList.remove(qId);
					}
				}else{
					continue;
				}
			}
			
			//创建试卷
			ExamPaper paper = new ExamPaper();
			paper.setExamPolicyId(policyId);
			paper.setGuid("0001");
			paper.setStatus(Bool.YES);
			paper.setUsed(Bool.NO);
			paper.setCreatedBy(userId);
			paper.setEnabledFlag(1);
			paper.setTime(examPolicy.getTime());
			paper.setTotleScore(examPolicy.getTotalScore());
			paper.setPassScore(examPolicy.getPassScore());
			Integer paperId = examPaperDAO.insert(paper);
			if(paperId == null || paperId < 1){
				return new CreatePaperResult("DATABASE.EXCEPTION", null, null);
			}
			
			//创建试题
			List<PaperQuestionRel> pqrList = new ArrayList<PaperQuestionRel>();
			for(ExamPolicyDetail detail : details){
				String type = detail.getType().toString()+ detail.getLev();
				int score = detail.getPerScore();
				List<Integer> qList = typeIds.get(type);
				for(int k = 0; k<qList.size(); k++){
					PaperQuestionRel pqr = new PaperQuestionRel();
					pqr.setPapersId(paperId);
					pqr.setQuestionId(qList.get(k));
					pqr.setScore(score);
					pqrList.add(pqr);
				}
			}
			paperQuestionRelDAO.insertBatch(pqrList);
			
			//试卷静态化
			List<PaperQuestion> questionList = examPaperDAO.getQuestionByPaperId(paperId);
			if(questionList == null || questionList.isEmpty()){
				return new CreatePaperResult("DATABASE.EXCEPTION", null, null);
			}
			//生成试卷唯一标识符作为试卷文件名
			String uuid = UUID.randomUUID().toString();
			String paperUrl = toStatic(uuid, examPolicy, questionList);
			if(paperUrl == null || "".equals(paperUrl)){
				return new CreatePaperResult("SERVER.EXCEPTION", null, null);
			}
			
			//更新试卷表
			ExamPaper updatePaper = new ExamPaper();
			updatePaper.setId(paperId);
			updatePaper.setUpdatedBy(userId);
			updatePaper.setPaperUrl(paperUrl);
			updatePaper.setGuid(uuid);
			examPaperDAO.update(updatePaper);
		}
		
		//分配总题库中的题目
		return new CreatePaperResult("PAPER.CREATE.SUCCESS", null, null);
	}
	
	/**
	 * Copy  the map
	 */
	private Map<String, List<Integer>> depthCopy(Map<String, List<Integer>> map){
		if(map == null){
			return null;
		}
		Map<String, List<Integer>> cloneMap = new HashMap<String, List<Integer>>();
		Set<String> keySet = map.keySet();
		for(Iterator<String> it = keySet.iterator(); it.hasNext(); ){
			String key = it.next();
			List<Integer> l = map.get(key);
			if(l != null){
				List<Integer> copyList = new ArrayList<Integer>();
				for(int i = 0; i<l.size(); i++){
					copyList.add(l.get(i));
				}
				cloneMap.put(key, copyList);
			}else{
				cloneMap.put(key, null);
			}
		}
		return cloneMap;
	}
	
	/**
	 * Copy the  list
	 */
	private List<Integer> depthCopyList(List<Integer> list){
		if(list == null){
			return null;
		}
		
		List<Integer> cloneList = new ArrayList<Integer>();
		for(Integer i : list){
			cloneList.add(i);
		}
		return cloneList;
	}

}
