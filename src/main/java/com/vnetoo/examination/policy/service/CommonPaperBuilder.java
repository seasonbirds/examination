package com.vnetoo.examination.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.vnetoo.common.enums.Bool;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.PaperQuestionRel;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;

public class CommonPaperBuilder extends AbstractPaperBuilder {
	
	public CreatePaperResult build() {
		if(userId == null || examPolicy == null || examKeyPointList == null || examKeyPointList.isEmpty()){
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
				
		//判断试题的数量是否满足试卷的设置
		Map<String, Integer>questionCountMap = examQuestionDAO.getQuestionTypeAndCountByKeyPointsAndRangeType(examKeyPointList, rangeType);
		Integer paperCount = examPolicy.getPaperCount();
		for(ExamPolicyDetail detail : details){
			String type = detail.getType().toString();
			String lev = detail.getLev().toString();
			Integer num = questionCountMap.get(type + lev);
			Integer needCount = detail.getQuestionCount();
			if(num == null || num < needCount){
				return new CreatePaperResult("PAPER.CREATE.QUESTION.NOTENOUGH", type, lev);
			}
		}
		
		//获取对应类型和知识点的试题, 并将类型、试题id列表保存在map中供随机选择试题使用
		HashMap<String, List<Integer>> questionIdMap = new HashMap<String, List<Integer>>();
		for(ExamPolicyDetail detail : details){
			String type = detail.getType().toString();
			String lev = detail.getLev().toString();
			List<Integer> questionIds = examQuestionDAO.getQuestionIds(type, lev, examKeyPointList, rangeType);
			questionIdMap.put(type+lev, questionIds);
		}
		
		//根据试卷套数创建试卷
		for(int i=0; i<paperCount; i++){
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
			
			//随机选择试题
			List<PaperQuestionRel>list = new ArrayList<PaperQuestionRel>();
			for(ExamPolicyDetail detail : details){
				String type = detail.getType().toString();
				String lev = detail.getLev().toString();
				List<Integer> questionIds = new ArrayList<Integer>(questionIdMap.get(type+lev));
				QuestionUtil util = new QuestionUtil(paperId, questionIds, detail.getQuestionCount(), detail.getPerScore());
				List<PaperQuestionRel> relList = util.createRel();
				if(relList == null || relList.isEmpty()){
					return new CreatePaperResult("DATABASE.EXCEPTION", null, null);
				}
				list.addAll(relList);
			}
			
			paperQuestionRelDAO.insertBatch(list);
			
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
		return new CreatePaperResult("PAPER.CREATE.SUCCESS", null, null);
	}

	/**
	 * 随机选择试题内部类
	 */
	class QuestionUtil {
		//试卷Id
		private Integer paperId;
		//试题ID列表
		private List<Integer>ids;
		//试卷中设置的试题数量
		private Integer number;
		//试题的分数
		private Integer score;
		
		QuestionUtil(Integer paperId, List<Integer>ids, Integer number, Integer score ){
			this.paperId = paperId;
			this.ids = ids;
			this.number = number;
			this.score = score;
		}
		
		/**
		 * 随机选择试题
		 */
		public List<PaperQuestionRel>createRel(){
			if(paperId == null || ids == null || number == null
					|| score == null || number > ids.size()){
				return null;
			}
			
			List<PaperQuestionRel> list = new ArrayList<PaperQuestionRel>();
			Random random = new Random();
			for(int i=0; i<number; i++){
				int index = random.nextInt(ids.size());
				Integer questionId = ids.get(index);
				
				PaperQuestionRel pqr = new PaperQuestionRel();
				pqr.setPapersId(paperId);
				pqr.setQuestionId(questionId);
				pqr.setScore(score);
				list.add(pqr);
				
				ids.remove(index);
			}
			return list;
		}
	}

}
