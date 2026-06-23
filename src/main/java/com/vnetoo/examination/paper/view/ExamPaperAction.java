package com.vnetoo.examination.paper.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.SystemGlobals;
import com.vnetoo.common.enums.Bool;
import com.vnetoo.common.view.BaseAction;

import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.FreeMarketUtil;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.bo.ScopeCourseRel;
import com.vnetoo.examination.app.bo.ScopePolicyRel;
import com.vnetoo.examination.app.bo.ScopeUserRel;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.app.service.ScopeCourseRelService;
import com.vnetoo.examination.app.service.ScopePolicyRelService;
import com.vnetoo.examination.app.service.ScopeUserRelService;
import com.vnetoo.examination.enums.QuestionType;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;
import com.vnetoo.examination.paper.bo.ExamStudentAnswerDetail;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.QuestionAnswer;
import com.vnetoo.examination.paper.service.ExamPaperService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerDetailService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerService;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;
import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.service.ExamAnswerService;
import com.vnetoo.webservice.HttpClientUtil;

import freemarker.ext.beans.ResourceBundleModel;

/**
 * @comment 试卷表Action
 * @author chenh
 * @date 2013-11-13 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="examPaper",
results={	@Result(name="search",	type="freemarker",location="examPaper/search.htm"),
				@Result(name="list",	type="freemarker",location="examPaper/list.htm"),	
				@Result(name="create",	type="freemarker",location="examPaper/create.htm"),
				@Result(name="show",	type="freemarker",location="examPaper/show.htm"),
				@Result(name="examResult",	type="freemarker",location="examPaper/examResult.htm"),
				@Result(name="error",	type="freemarker",location="error.htm")})
public class ExamPaperAction extends BaseAction<ExamPaper>{
	private static final Logger log = Logger.getLogger(ExamPaperAction.class);
	
	@Resource
	private ScopePolicyRelService scopePolicyRelService;
	@Resource
	private ExamPaperService examPaperService;
	@Resource
	private ExamPolicyService examPolicyService;
	@Resource
	private ExamAnswerService examAnswerService;
	@Resource
	private ExamStudentAnswerService examStudentAnswerService;
	@Resource
	private ExamStudentAnswerDetailService examStudentAnswerDetailService;
	@Resource
	private Map<String, String> scoreCallbackUrls;
	@Resource
	private AppScopeService appScopeService;
	@Resource
	private ScopeCourseRelService scopeCourseRelService;
	@Resource
	private ScopeUserRelService scopeUserRelService;
	
	private ExamPaper examPaper = new ExamPaper();
	
	private ExamPolicy examPolicy;
	
	//单选题列表
	private List<PaperQuestion> singleList = new ArrayList<PaperQuestion>();
	//多选题列表
	private List<PaperQuestion> multList = new ArrayList<PaperQuestion>();
	//判断题列表
	private List<PaperQuestion> judgeList = new ArrayList<PaperQuestion>();
	//填空题列表
	private List<PaperQuestion> clozeList = new ArrayList<PaperQuestion>();
	//名称解释题列表
	private List<PaperQuestion> explainList = new ArrayList<PaperQuestion>();
	//问答题列表
	private List<PaperQuestion> qnqList = new ArrayList<PaperQuestion>();

	private Integer scopeId;
	
	private String[] answers;
	
	private String ids;
	
	private Integer paperScore = 0;
	
	private String permission;
	
	private String businessInfo;
	
	private String examId;
	
	private String studentId;
	/**************************************************************************************/
		
	/**
	 * 进入查询试卷表页面
	 * /jsp/examPaper/list.htm
	 */
	public String search() 
	{		
		query();
		return RESULT_SEARCH;
	}
		
	/**
	 * 查询试卷表
	 * /jsp/examPaper/data.jsp
	 */
	public String query() {				
		examPaperService.pageQuery(examPaper,paginator);
		return RESULT_LIST;
	}
	
	/**
	 * 进入试卷表修改页面
	 * /jsp/examPaper/CreateExamPaper.jsp 
	 */
	public String toCreateOrUpdate()
	{
		if(examPaper.getId()!=null&&examPaper.getId().longValue()>0){//修改
			examPaper=examPaperService.findById(examPaper.getId());			
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存试卷表	
	 */
	public String save()
	{
		if(examPaper.getId()!=null&&examPaper.getId().longValue()>0){
//			ExamPaper po=examPaperService.findById(examPaper.getId());			
			//po.setName(examPaper.getName());
			
			//po.setUpdateBy(new Integer(0));			
			//examPaper=po;
		}
		else{
			//examPaper.setCreateBy(new Integer(0));
			//examPaper.setUpdateBy(examPaper.getCreateBy());
		}
		try{
			examPaperService.saveOrUpdate(examPaper);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除试卷表
	 */
	public String remove() 
	{
		try{
			examPaperService.removeLogic(examPaper.getId());			
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除试卷表
	 */
	public String batchRemove() 
	{
		try{
			for(int i=0;i<examPaper.getIds().length;i++){
				examPaperService.removeLogic(examPaper.getIds()[i]);		
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 查看试卷试题详情
	 * @author Lisz
	 * @date  2014-1-9  下午2:56:34
	 */
	public String details(){
		try{
			if(examPaper.getId() == null || examPaper.getId() < 1){
				setResponseFlag(-1);
				setResponseMsg("No data");
			}else{
				//获取试卷
				examPaper = examPaperService.findById(examPaper.getId());
				//获取试卷对应的策略
				examPolicy = examPolicyService.findById(examPaper.getExamPolicyId());                                                                            
				
				List<PaperQuestion> list = examPaperService.getQuestionByPaperId(examPaper.getId());
				if(list != null && !list.isEmpty()){
					for(PaperQuestion question : list){
						QuestionType type = question.getQuestionType();
						if(type == QuestionType.SINGLE){
							ExamAnswer examAnswer = new ExamAnswer();
							examAnswer.setQuestionId(question.getQuestionId());
							List<ExamAnswer> answerList = examAnswerService.dynamicQuery(examAnswer);
							question.setAnswerList(answerList);
							singleList.add(question);
						}else if(type == QuestionType.MULT){
							ExamAnswer examAnswer = new ExamAnswer();
							examAnswer.setQuestionId(question.getQuestionId());
							List<ExamAnswer> answerList = examAnswerService.dynamicQuery(examAnswer);
							question.setAnswerList(answerList);
							multList.add(question);
						}else if(type == QuestionType.JUDGE){
							ExamAnswer examAnswer = new ExamAnswer();
							examAnswer.setQuestionId(question.getQuestionId());
							List<ExamAnswer> answerList = examAnswerService.dynamicQuery(examAnswer);
							question.setAnswerList(answerList);
							judgeList.add(question);
						}else if(type == QuestionType.CLOZE){
							clozeList.add(question);
						}else if(type == QuestionType.EXPLAIN){
							explainList.add(question);
						}else if(type == QuestionType.QNQ){
							qnqList.add(question);
						}
					}
				}
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg("No data");
		}
		
		return RESULT_SHOW;
	}
	
	/**
	 * 获取考试页面
	 */
	public String goExam(){
		ResourceBundleModel rbm = this.getResourceBundle();
		try {
			Integer sId = this.getCurrentUser().getId();
			//系统作为一个单独的非完整子系统运行时，考试权限控制由其他系统提供
			if(!SystemGlobals.getValueBool(ExamConstant.INTEGRATED_RUNNING)){
				String validStudentUrl = SystemGlobals.getValue(ExamConstant.VALID_STUDENT_URL);
				boolean denied = true;
				if(validStudentUrl != null && !"".equals(validStudentUrl.trim())){
					if(!isDigest(examId) || permission == null || studentId == null || "".equals(studentId.trim())){
						setResponseMsg(rbm.format("PARAMETER.ERROR", null));
						return "error";
					}
					
					HttpClientUtil util = new HttpClientUtil(validStudentUrl);
					Map<String, String> map = new HashMap<String, String>();
					map.put("studentId", String.valueOf(studentId));
					map.put("permission", permission);
					String response = util.postRequset(false, map);
					if(response != null && response.equals("true")){
						denied = false;
					}
				}
				if(denied){
					setResponseMsg(rbm.format("PERMISSION_DENIED", null));
					return "error";
				}else{
					scopeId = Integer.parseInt(examId);
					//考试和考生是否存在关系（存在：考生已经不止一次参见过该考试）
					ScopeUserRel suRel = new ScopeUserRel();
					suRel.setScopeId(scopeId);
					List<ScopeUserRel> suRelList = scopeUserRelService.dynamicQuery(suRel);
					if(suRelList == null || suRelList.isEmpty()){
						//插入考试和考生的对应关系
						ScopeUserRel scopeUserRel = new ScopeUserRel();
						scopeUserRel.setScopeId(scopeId);
						scopeUserRel.setUserId(sId);
						List<ScopeUserRel> scopeUserRelList = scopeUserRelService.dynamicQuery(scopeUserRel);
						if(scopeUserRelList == null || scopeUserRelList.isEmpty()){
							scopeUserRelService.insertRel(scopeUserRel);
						}
					}
				}
			}
			
			if(scopeId == null || scopeId <= 0){
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
				return "error";
			}
			//获取scopeId对应的考试策略
			ScopePolicyRel rel = new ScopePolicyRel();
			rel.setScopeId(scopeId);
			List<ScopePolicyRel> relList = scopePolicyRelService.dynamicQuery(rel);
			//考试和策略的关系不存在
			if(relList == null || relList.isEmpty()){
				setResponseMsg(rbm.format("EXAM_NOEXIST_PAPER", null));
				return "error";
			}
			//当前支持一个策略，暂不支持多个策略
			rel = relList.get(0);
			ExamPolicy policy = examPolicyService.findById(rel.getPolicyId());
			//策略不存在
			if(policy == null){
				setResponseMsg(rbm.format("EXAM_NOEXIST_PAPER", null));
				return "error";
			}
			//当前时间在考试开始时间之前
			Date current = new Date();
			if(current.before(policy.getExamTimeStart())){
				setResponseMsg(rbm.format("EXAM_NOT_START", null));
				return "error";
			}
			//当前时间在考试结束时间之后
			if(current.after(policy.getExamTimeEnd())){
				setResponseMsg(rbm.format("EXAM_ALREADY_CLOSE", null));
				return "error";
			}
			
			//获取scopeId下的所有试卷
			List<Integer> paperIds = examPaperService.getPaperIdsByScopeId(scopeId);
			//试卷不存在
			if(paperIds == null || paperIds.isEmpty()){
				setResponseMsg(rbm.format("EXAM_NOEXIST_PAPER", null));
				return "error";
			}

			//先判断学生是否有答题记录
			List<ExamStudentAnswer> esaList = examStudentAnswerService.getStudentAnswerByPaperIdAndSId(sId, paperIds);
			if(esaList !=null && !esaList.isEmpty()){
				ExamStudentAnswer esa = esaList.get(0);
				//未交卷，转向继续答题
				if(esa.getStatus() == 0){
					ServletActionContext.getResponse().sendRedirect("examPaper!goContinueExam.action?scopeId=" + scopeId);
					return null;
				//已交卷，转向查看结果	（已交卷，可以继续考试）
				}else if(esa.getStatus() == 1){
					//ServletActionContext.getResponse().sendRedirect("examPaper!showExamResult.action?scopeId=" + scopeId);
					//更新原有答题记录(一个学生一门考试只能有一条有效的答题记录)
					ExamStudentAnswer oldStudentAnswer = esaList.get(0);
					oldStudentAnswer.setEnabledFlag(0);
					examStudentAnswerService.saveOrUpdate(oldStudentAnswer);
					//试卷份数大于1，排除上次试卷Id,使本次考试的试卷与上次不相同，达到相邻两次考试试卷不一样的目的
					//试卷份数为1，则重复考相同的试卷
					if(paperIds.size() > 1){
						paperIds.remove(oldStudentAnswer.getPapersId());
					}
				}else{
					setResponseMsg(rbm.format("DATABASE.EXCEPTION", null));
					return "error";
				}
			}
			//随机获取试卷
			Random random = new Random();
			Integer paperId = paperIds.get(random.nextInt(paperIds.size()));
			ExamPaper paper = examPaperService.findById(paperId);
			
			//初始化学生答案表
			ExamStudentAnswer studentAnswer = new ExamStudentAnswer();
			studentAnswer.setEnabledFlag(1);
			studentAnswer.setPapersId(paperId);
			studentAnswer.setStudentId(sId);
			studentAnswer.setStatus(0);
			studentAnswer.setCreatedBy(sId);
			if(businessInfo != null && !"".equals(businessInfo.trim())){
				studentAnswer.setBusinessInfo(businessInfo);
			}
			Integer studentAnswerId = examStudentAnswerService.saveOrUpdate(studentAnswer);
			if(studentAnswerId == null || studentAnswerId <= 0){
				setResponseMsg(rbm.format("EXAM_INIT_ERROR", null));
				return "error";
			}else{
				//更新试卷表
				paper.setUsed(Bool.YES);
				examPaperService.saveOrUpdate(paper);
				//跳转至考试静态页面
				ServletActionContext.getResponse().sendRedirect(paper.getPaperUrl());
			}
		} catch (IOException e) {
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
			return "error";
		}
		return null;
	}
	
	/**
	 * 开始答题时,获取考试剩余时间
	 */
	public String getRemainTime(){
		JSONObject json = null;
		if(examPaper.getGuid() == null || "".equals(examPaper.getGuid())){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
		}else{
			//通过guid查找策略
			ExamPolicy policy = examPolicyService.getPolicyByPaperGuid(examPaper.getGuid());
			if(policy != null){
				json = new JSONObject();
				Date start = policy.getExamTimeStart();
				Date end = policy.getExamTimeEnd();
				Date current = Calendar.getInstance().getTime();
				if(current.before(start) || current.after(end)){
					json.put("time", 0);
				}else{
					long endTime = end.getTime();
					long currentTime =  current.getTime();
					long examTime = policy.getTime() * 60 * 1000;
					long remain = examTime - 0;
					 remain = endTime - currentTime > remain ? remain : endTime - currentTime;
					json.put("time",  remain/1000);
				}
			}else{
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			}
		}
		writeJsonDataResponse(json);
		return null;
	}
	
	/**
	 * 获取断点续考页面
	 * @author Lisz
	 * @date  2014-1-17  下午3:37:26
	 */
	public String goContinueExam(){
		ResourceBundleModel rbm = this.getResourceBundle();
		try {
			if(scopeId == null || scopeId <= 0){
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
				return "error";
			}else{
				//获取scopeId下的所有试卷
				List<Integer> paperIds = examPaperService.getPaperIdsByScopeId(scopeId);
				if(paperIds == null || paperIds.isEmpty()){
					setResponseMsg(rbm.format("EXAM_NOEXIST_PAPER", null));
					return "error";
				}else{
					//查询当前用户对应该scopeId下的所有试卷是否有未交卷的答题记录
					List<ExamStudentAnswer> stuentAnswerIds = examStudentAnswerService
								.getStudentAnswerByPaperIdAndSId(this.getCurrentUser().getId(), paperIds);
					if(stuentAnswerIds != null && !stuentAnswerIds.isEmpty()){
						ExamStudentAnswer answer = stuentAnswerIds.get(0);
						if(answer.getStatus() == 0){
							Integer paperId = stuentAnswerIds.get(0).getPapersId();
							ExamPaper paper = examPaperService.findById(paperId);
							ServletActionContext.getResponse().sendRedirect(paper.getPaperUrl() + "?type=1");
						}else if(answer.getStatus() == 1){
							ServletActionContext.getResponse().sendRedirect("examPaper!showExamResult.action?scopeId=" + scopeId);
						}else{
							setResponseMsg(rbm.format("DATABASE.EXCEPTION", null));
							return "error";
						}
					}else{
						setResponseMsg(rbm.format("DATABASE.EXCEPTION", null));
						return "error";
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 断点保存考试结果
	 */
	public String saveExamResult(){
		try{
			if(examPaper.getGuid() == null || "".equals(examPaper.getGuid())){
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			}else{
				//通过guid查找试卷
				List<ExamPaper> paperList = examPaperService.dynamicQuery(examPaper);
				if(paperList != null && !paperList.isEmpty()){
					Integer userId = this.getCurrentUser().getId();
					ExamPaper paper = paperList.get(0);
					//通过试卷Id和学生Id查找有效的答题记录
					ExamStudentAnswer studentAnswer = new ExamStudentAnswer();
					studentAnswer.setPapersId(paper.getId());
					studentAnswer.setStudentId(userId);
					studentAnswer.setEnabledFlag(1);
					List<ExamStudentAnswer> answerList = examStudentAnswerService.dynamicQuery(studentAnswer);
					if(answerList != null && !answerList.isEmpty()){
						//保存答题记录表
						studentAnswer = answerList.get(0);
						studentAnswer.setAnswerId(ids);
						studentAnswer.setUpdatedBy(userId);
						Integer studentAnswerId = examStudentAnswerService.saveOrUpdate(studentAnswer);
						if(studentAnswerId == null || studentAnswerId < 1){
							this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						}else{
							//客观题存在，更新或者插入学生答题详情表
							if(answers !=null && answers.length > 0){
								for(String answer : answers){
									String[] questionAnswer =  answer.split(",");
									ExamStudentAnswerDetail examStudentAnswerDetail = new ExamStudentAnswerDetail();
									examStudentAnswerDetail.setStudentAnswerId(studentAnswerId);
									examStudentAnswerDetail.setQuestionId(Integer.parseInt(questionAnswer[0]));
									List<ExamStudentAnswerDetail> detailList = examStudentAnswerDetailService.dynamicQuery(examStudentAnswerDetail);
									//答题详情存在则更新
									if(detailList != null && !detailList.isEmpty()){
										examStudentAnswerDetail = detailList.get(0);
									}
									examStudentAnswerDetail.setAnswerContent(questionAnswer[1]);
									examStudentAnswerDetailService.saveOrUpdate(examStudentAnswerDetail);
								}
							}
						}
					}else{
						this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					}
				}else{
					this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				}
			}
		}catch(Exception e){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
		}
		this.writeResponse();
		return null;
	}
	
	/**
	 * 交卷时保存试卷结果并计算客观题分数
	 */
	public String saveFinalExamResult(){
		if(examPaper.getGuid() == null || "".equals(examPaper.getGuid()) || answers == null || answers.length <= 0){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
		}else{
			//通过guid查找试卷
			List<ExamPaper> paperList = examPaperService.dynamicQuery(examPaper);
			if(paperList != null && !paperList.isEmpty()){
				Integer userId = this.getCurrentUser().getId();
				ExamPaper paper = paperList.get(0);
				//通过试卷Id和学生Id查找有效的答题记录
				ExamStudentAnswer studentAnswer = new ExamStudentAnswer();
				studentAnswer.setPapersId(paper.getId());
				studentAnswer.setStudentId(userId);
				studentAnswer.setEnabledFlag(1);
				List<ExamStudentAnswer> answerList = examStudentAnswerService.dynamicQuery(studentAnswer);
				if(answerList != null && !answerList.isEmpty()){
					studentAnswer = answerList.get(0);
					Integer studentAnswerId = studentAnswer.getId();
					//计算客观题分数
					int score = 0;
					String ids = "";
					//获取试卷客观题的所有正确答案
					Map<Integer, List<QuestionAnswer>> correctAnswerMap = examPaperService.getAnswersByPaperId(paper.getId());
					//遍历学生答案，客观题计算总分数，主观题更新答题记录详情
					boolean isExistSubjectQuestion = false;
					for(String answer : answers){
						String[] tmp = answer.split(",");
						Integer type = Integer.parseInt(tmp[0]);
						Integer questionId = Integer.parseInt(tmp[1]);
						//客观题
						if(type == 1){
							//TODO
							if(tmp[2].indexOf("-") > 0){
								ids += tmp[2].replace("-", ",") + ",";
							}else{
								ids += tmp[2] + ",";
							}
							List<QuestionAnswer> correctAnswerList = correctAnswerMap.get(questionId);
							if(correctAnswerList != null){
								//单选题、判断题和只有一个答案的多选题
								if(correctAnswerList.size() == 1){
									QuestionAnswer correctAnswer = correctAnswerList.get(0);
									if(String.valueOf(correctAnswer.getAnswerId()).equals(tmp[2])){
										score += correctAnswer.getScore();
									}
								}else{
									//多个答案的多选题，将正确答案和学生的答案转换为数组再比较
									/*Integer[] correctAnswersInteger = new Integer[correctAnswerList.size()];
									for(int i=0; i<correctAnswersInteger.length; i++){
										correctAnswersInteger[i] = correctAnswerList.get(i).getAnswerId();
									}
									
									String[] answerArrayString = (tmp[3].split("-"));
									Integer[] answerArrayInteger = new Integer[answerArrayString.length];
									for(int i = 0; i<answerArrayString.length; i++){
										answerArrayInteger[i] = Integer.parseInt(answerArrayString[i]);
									}
									
									Arrays.sort(answerArrayInteger);
									Arrays.sort(correctAnswersInteger);
									if(Arrays.equals(answerArrayInteger, correctAnswersInteger)){
										score += correctAnswerList.get(0).getScore();
									}*/
									//TODO
									String[] answerArrayString = (tmp[2].split("-"));
									if(answerArrayString.length == correctAnswerList.size()){
										//正确答案id列表
										List<Integer> correctAnswerIds = new ArrayList<Integer>();
										for(QuestionAnswer questionAnswer : correctAnswerList){
											correctAnswerIds.add(questionAnswer.getAnswerId());
										}
										//学生答案都在正确列表中
										int i = 0;
										for(; i<answerArrayString.length; i++){
											if(!correctAnswerIds.contains(Integer.parseInt(answerArrayString[i]))){
												break;
											}
										}
										if(i == answerArrayString.length){
											score += correctAnswerList.get(0).getScore();
										}
									}
								}
							}
						//主观题
						}else if(type == 2){
							isExistSubjectQuestion = true;
							ExamStudentAnswerDetail examStudentAnswerDetail = new ExamStudentAnswerDetail();
							examStudentAnswerDetail.setStudentAnswerId(studentAnswerId);
							examStudentAnswerDetail.setQuestionId(Integer.parseInt(tmp[1]));
							List<ExamStudentAnswerDetail> detailList = examStudentAnswerDetailService.dynamicQuery(examStudentAnswerDetail);
							//答题详情存在则更新
							if(detailList != null && !detailList.isEmpty()){
								examStudentAnswerDetail = detailList.get(0);
							}
							examStudentAnswerDetail.setAnswerContent(tmp[2]);
							examStudentAnswerDetailService.saveOrUpdate(examStudentAnswerDetail);
						}
					}
					//更新学生答题表
					studentAnswer.setAnswerId(ids.substring(0, ids.length() - 1));
					studentAnswer.setStatus(1);
					studentAnswer.setScore(score);
					studentAnswer.setUpdatedBy(userId);
					examStudentAnswerService.saveOrUpdate(studentAnswer);
					//只存在客观题时，回调成绩接口
					if(!SystemGlobals.getValueBool(ExamConstant.INTEGRATED_RUNNING) && !isExistSubjectQuestion){
						ExamPolicy policy = examPolicyService.findById(paper.getExamPolicyId());
						ScopePolicyRel scopePolicyRel = new ScopePolicyRel();
						scopePolicyRel.setPolicyId(policy.getId());
						List<ScopePolicyRel> relList = scopePolicyRelService.dynamicQuery(scopePolicyRel);
						AppScope scope = appScopeService.findById(relList.get(0).getScopeId());
						String callbackUrl = scoreCallbackUrls.get(scope.getType());
						if(callbackUrl == null){
							callbackUrl = scoreCallbackUrls.get("DEFAULT");
						}
						if(callbackUrl != null){
							ScopeCourseRel scopeCourseRel = new ScopeCourseRel();
							scopeCourseRel.setScopeId(scope.getId());
							List<ScopeCourseRel>  scopeCourseRelList = scopeCourseRelService.dynamicQuery(scopeCourseRel);
							//直接使用学生的答题记录信息，无须重新查询
							//ExamStudentAnswer sa = examStudentAnswerService.findById(studentAnswer.getId());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							HttpClientUtil util = new HttpClientUtil(callbackUrl);
							Map<String, String> map = new HashMap<String, String>();
							map.put("studentId", String.valueOf(userId));
							map.put("courseId", String.valueOf(scopeCourseRelList.get(0).getCourseId()));
							map.put("score", String.valueOf(score));
							map.put("examId", String.valueOf(scope.getId()));
							map.put("beginTime", sdf.format(studentAnswer.getCreationDate()));
							map.put("endTime", sdf.format(new Date()));
							map.put("businessInfo", studentAnswer.getBusinessInfo());
							util.postRequset(false, map);
						}
					}
				}else{
					this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				}
			}else{
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			}
		}
		this.writeResponse();
		return null;
	}
	
	/**
	 * 获取学生的答题状态（答案，保存时间）
	 */
	public String getStudentAnswerStatus(){
		JSONObject json = null;
		if(examPaper.getGuid() == null || "".equals(examPaper.getGuid())){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
		}else{
			//通过guid查找试卷
			List<ExamPaper> paperList = examPaperService.dynamicQuery(examPaper);
			if(paperList != null && !paperList.isEmpty()){
				Integer userId = this.getCurrentUser().getId();
				ExamPaper paper = paperList.get(0);
				//通过试卷Id和学生Id查找有效的答题记录
				ExamStudentAnswer studentAnswer = new ExamStudentAnswer();
				studentAnswer.setPapersId(paper.getId());
				studentAnswer.setStudentId(userId);
				studentAnswer.setEnabledFlag(1);
				List<ExamStudentAnswer> answerList = examStudentAnswerService.dynamicQuery(studentAnswer);
				if(answerList != null && !answerList.isEmpty()){
					studentAnswer = answerList.get(0);
					json = new JSONObject();
					json.put("ids", studentAnswer.getAnswerId());
					ExamPolicy policy = examPolicyService.getPolicyByPaperGuid(examPaper.getGuid());
					long examTime = policy.getTime() * 60 * 1000;
					long remain = examTime - (System.currentTimeMillis() - studentAnswer.getCreationDate().getTime());
					remain = remain < 0 ? 0 : ((policy.getExamTimeEnd().getTime() - System.currentTimeMillis()) > remain 
							? remain : (policy.getExamTimeEnd().getTime() - System.currentTimeMillis()));
					json.put("time", remain/1000);
					ExamStudentAnswerDetail detail = new ExamStudentAnswerDetail();
					detail.setStudentAnswerId(studentAnswer.getId());
					List<ExamStudentAnswerDetail> detailList = examStudentAnswerDetailService.dynamicQuery(detail);
					if(detailList != null && !detailList.isEmpty()){
						JSONArray jsonArray = new JSONArray();
						for(ExamStudentAnswerDetail answerDetail : detailList){
							JSONObject item = new JSONObject();
							item.put("questionId", answerDetail.getQuestionId());
							item.put("content", answerDetail.getAnswerContent());
							jsonArray.add(item);
						}
						json.put("detail", jsonArray);
					}
				}else{
					this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				}
			}else{
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			}
		}
		
		this.writeJsonDataResponse(json);
		return null;
	}
	
	/**
	 * 查看考试结果
	 */
	public String showExamResult(){
		try{
			if((examPaper.getGuid() == null || "".equals(examPaper.getGuid())) && (scopeId == null || scopeId <= 0)){
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			}else{
				ExamStudentAnswer studentAnswer = null;
				//通过guid查找试卷
				if(examPaper.getGuid()!= null && !"".equals(examPaper.getGuid())){
					examPaper = examPaperService.dynamicQuery(examPaper).get(0);
					//通过试卷Id和学生Id查找学生有效的答题记录
					studentAnswer = new ExamStudentAnswer();
					studentAnswer.setPapersId(examPaper.getId());
					studentAnswer.setStudentId(this.getCurrentUser().getId());
					studentAnswer.setEnabledFlag(1);
					studentAnswer = examStudentAnswerService.dynamicQuery(studentAnswer).get(0);
				//通过scopeId查找试卷
				}else if(scopeId != null && scopeId > 0){
					List<Integer> paperIds = examPaperService.getPaperIdsByScopeId(scopeId);
					//查询当前用户对应该scopeId下的有效答题记录（有且仅有一条）
					List<ExamStudentAnswer> stuentAnswerIds = examStudentAnswerService
								.getStudentAnswerByPaperIdAndSId(this.getCurrentUser().getId(), paperIds);
					studentAnswer = stuentAnswerIds.get(0);
					examPaper = examPaperService.findById(studentAnswer.getPapersId());
				}
				
				Integer paperId = examPaper.getId();
				//查找试卷对应的策略
				examPolicy = examPolicyService.findById(examPaper.getExamPolicyId());
				//查找学生答题详情
				ExamStudentAnswerDetail examStudentAnswerDetail = new ExamStudentAnswerDetail();
				examStudentAnswerDetail.setStudentAnswerId(studentAnswer.getId());
				List<ExamStudentAnswerDetail>answerDetailList = examStudentAnswerDetailService.dynamicQuery(examStudentAnswerDetail);
				
				//获取试卷所有试题
				List<PaperQuestion> questionList = examPaperService.getQuestionByPaperId(paperId);
				//获取试卷试题所有答案
				List<ExamAnswer> answerList = examAnswerService.getAllAnswersByPaperId(paperId);
				
				//试题对应的答案
				Map<Integer, List<ExamAnswer>> questionAnswerMap = new HashMap<Integer, List<ExamAnswer>>();
				//学生答题的客观题答案
				Map<Integer, List<Integer>> studentObjectiveAnswerMap = new HashMap<Integer, List<Integer>>();
				//学生答题的主观题答案
				Map<Integer, ExamStudentAnswerDetail> studentSubjectiveAnswerMap = new HashMap<Integer, ExamStudentAnswerDetail>();
				//构造试题和答案、学生答题的主观、客观题答案对应的map
				String answerIds = studentAnswer.getAnswerId() != null ? studentAnswer.getAnswerId() : "";
				List<String> answerIdList =Arrays.asList(answerIds.split(","));
				for(ExamAnswer examAnswer : answerList){
					Integer questionId = examAnswer.getQuestionId();
					//试题和答案对应的Map
					if(questionAnswerMap.containsKey(questionId)){
						questionAnswerMap.get(questionId).add(examAnswer);
					}else{
						List<ExamAnswer> list = new ArrayList<ExamAnswer>();
						list.add(examAnswer);
						questionAnswerMap.put(questionId, list);
					}
					//学生答题的客观题的试题和答案对应的map
					Integer answerId = examAnswer.getId();
					if(answerIdList.contains(String.valueOf(answerId))){
						if(studentObjectiveAnswerMap.containsKey(questionId)){
							studentObjectiveAnswerMap.get(questionId).add(answerId);
						}else{
							List<Integer> list = new ArrayList<Integer>();
							list.add(answerId);
							studentObjectiveAnswerMap.put(questionId, list);
						}
					}
				}
				//学生答题的主观题的试题和答案对应的map
				for(ExamStudentAnswerDetail answerDetail : answerDetailList){
					studentSubjectiveAnswerMap.put(answerDetail.getQuestionId(), answerDetail);
				}
				//TODO
				//计算考试的得分：总分=客观题得分+主观题得分
				paperScore += (studentAnswer.getScore() == null ? 0 : studentAnswer.getScore());
				//设置考题、学生的答案、试题的正确答案、试题的得分
				for(PaperQuestion paperQuestion : questionList){
					QuestionType type = paperQuestion.getQuestionType();
					Integer questionId = paperQuestion.getQuestionId();
					List<ExamAnswer> al = questionAnswerMap.get(questionId);
					switch(type){
						case SINGLE :
						case MULT :
						case JUDGE :
							List<Integer> sal = studentObjectiveAnswerMap.get(questionId);
							//学生答题的答案列表
							List<ExamAnswer> qal = new ArrayList<ExamAnswer>();
							//正确答案列表
							List<ExamAnswer> cqal = new ArrayList<ExamAnswer>();
							if(sal != null && !sal.isEmpty()){
								for(ExamAnswer answer : al){
									if(sal.contains(answer.getId())){
										qal.add(answer);
									}
								}
							}
							for(ExamAnswer answer : al){
								if("1".equals(answer.getValid())){
									cqal.add(answer);
								}
							}
							//试题得分
							if(qal.size() != cqal.size()){
								paperQuestion.setAnswerScore(0);
							}else{
								int i = 0;
								for(; i<cqal.size(); i++){
									if(!sal.contains(cqal.get(i).getId())){
										break;
									}
								}
								if(i != cqal.size()){
									paperQuestion.setAnswerScore(0);
								}else{
									paperQuestion.setAnswerScore(paperQuestion.getQuestionScore());
								}
							}
							paperQuestion.setqAnswerList(al);
							paperQuestion.setAnswerList(qal);
							paperQuestion.setCorrectAnswerList(cqal);
							if(type == QuestionType.SINGLE){
								singleList.add(paperQuestion);
							}else if(type == QuestionType.MULT){
								multList.add(paperQuestion);
							}else{
								judgeList.add(paperQuestion);
							}
							break;
						case CLOZE :
						case EXPLAIN :
						case QNQ :
							paperQuestion.setCorrectAnswerList(questionAnswerMap.get(questionId));
							ExamStudentAnswerDetail esad = studentSubjectiveAnswerMap.get(questionId);
							List<ExamAnswer> l = new ArrayList<ExamAnswer>();
							if(esad != null){
								ExamAnswer ead = new ExamAnswer();
								ead.setQuestionId(esad.getQuestionId());
								ead.setContent(esad.getAnswerContent());
								l.add(ead);
								if(esad.getScore() != null){
									paperQuestion.setAnswerScore(esad.getScore());
								}
							}
							paperQuestion.setAnswerList(l);
							if(type == QuestionType.CLOZE){
								clozeList.add(paperQuestion);
							}else if(type == QuestionType.EXPLAIN){
								explainList.add(paperQuestion);
							}else{
								qnqList.add(paperQuestion);
							}
							
							paperScore += paperQuestion.getQuestionScore();
							break;
						default :
								break;
					}
				}
			}
		}catch(Exception e){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			this.setResponseMsg("服务器异常");
		}
		return "examResult";
	}
	
	/**
	 * 批量下载试卷
	 */
	public String batchDownloadPaper(){
		File tmpDictory = null;
		ZipOutputStream out = null;
		FileInputStream rfis = null;
		ServletOutputStream sout = null;
		try{
			if(examPaper != null && examPaper.getIds() != null && examPaper.getIds().length > 0){
				Integer[] paperIds = examPaper.getIds();
				//构建试卷和策略的关系，一个策略可能对应多份试卷
				Map<Integer, PolicyPaperRef> policyPaperRefMap = new HashMap<Integer, PolicyPaperRef>();
				for(int i=0; i<paperIds.length; i++){
					ExamPaper ep = examPaperService.findById(paperIds[i]);
					Integer policyId  = ep.getExamPolicyId();
					PolicyPaperRef docFileNameUtil = null;
					if(policyPaperRefMap.containsKey(policyId)){
						docFileNameUtil = policyPaperRefMap.get(policyId);
						docFileNameUtil.getPaperIds().add(paperIds[i]);
					}else{
						docFileNameUtil = new PolicyPaperRef();
						ExamPolicy examPolicy = examPolicyService.findById(policyId);
						docFileNameUtil.setPolicy(examPolicy);
						List<Integer>paperIdList = new ArrayList<Integer>();
						paperIdList.add(paperIds[i]);
						docFileNameUtil.setPaperIds(paperIdList);
						policyPaperRefMap.put(policyId, docFileNameUtil);
					}
				}
				
				//创建临时目录
				long timestamp = Calendar.getInstance().getTimeInMillis();
				String path = servletCtx.getRealPath("/") + File.separator + timestamp;
				tmpDictory = new File(path);
				tmpDictory.mkdir();
				
				//批量生成.doc文件
				Set<Integer> keySet = policyPaperRefMap.keySet();
				for(Iterator<Integer> it = keySet.iterator(); it.hasNext(); ){
					PolicyPaperRef docFileNameUtil = policyPaperRefMap.get(it.next());
					ExamPolicy policy = docFileNameUtil.getPolicy();
					List<Integer> paperIdsList = docFileNameUtil.getPaperIds();
					if(paperIdsList.size() == 1){
						//策略下只有一份试卷，试卷名为：策略名.doc
						cleanCollection();
						createWordFile(paperIdsList.get(0), policy, path + File.separator + policy.getName() + ".doc");
					}else{
						//策略下有多份试卷，试卷名为：策略名+索引值+.doc
						for(int i=0; i<paperIdsList.size(); i++){
							//每生成一份试卷前清除集合
							cleanCollection();
							createWordFile(paperIdsList.get(i), policy, path + File.separator + policy.getName() + "_" +(i + 1) + ".doc");
						}
					}
				}
				
				//将生成的.doc文件压缩
				File[] files = tmpDictory.listFiles();
				String zipName = timestamp + ".zip";
				out = new ZipOutputStream(new FileOutputStream(path + File.separator + zipName));
				byte[] buffer = new byte[65536];
				for(int i=0; i<files.length; i++){
					out.putNextEntry(new ZipEntry(files[i].getName()));
					FileInputStream fis = new FileInputStream(files[i]);
					int length;
					while((length = fis.read(buffer)) > 0){
						out.write(buffer, 0, length);
					}
					out.closeEntry();
					fis.close();
				}
				out.flush();
				out.close();
				
				//将压缩文件返回客户端
				rfis = new FileInputStream(path + File.separator + zipName);
				HttpServletResponse response=ServletActionContext.getResponse();
				response.setContentType("APPLICATION/OCTET-STREAM");
				response.setHeader("Content-Disposition", "attachment; filename=\""+zipName+"\"");
				sout = response.getOutputStream();
				int len;
				while((len = rfis.read(buffer)) > 0){
					sout.write(buffer, 0, len);
					response.flushBuffer();
				}
				rfis.close();
				sout.close();
			}
		}catch(Exception e){
			log.error("Exception", e);
		}finally{
			try {
				//关闭文件流
				if(out != null){
					out.close();
					out = null;
				}
				if(rfis != null){
					rfis.close();
					rfis = null;
				}
				if(sout != null){
					sout.close();
					sout = null;
				}
				//删除临时目录下的文件和它本身
				if(tmpDictory !=null){
					File[] files = tmpDictory.listFiles();
					for(File file : files){
						file.delete();
					}
					tmpDictory.delete();
				}
			} catch (IOException e) {
				log.error("IOException", e);
			}
		}
		return null;
	}
	
	/**
	 * 使用freemarket模板生成.doc文件
	 */
	private void createWordFile(Integer paperId, ExamPolicy examPolicy, String fileName) throws FileNotFoundException{
		List<PaperQuestion> list = examPaperService.getQuestionByPaperId(paperId);
		if(list != null && !list.isEmpty()){
			Map<String, Object> map = new HashMap<String, Object>();
			for(PaperQuestion question : list){
				QuestionType type = question.getQuestionType();
				if(type == QuestionType.SINGLE){
					ExamAnswer examAnswer = new ExamAnswer();
					examAnswer.setQuestionId(question.getQuestionId());
					List<ExamAnswer> answerList = examAnswerService.dynamicQuery(examAnswer);
					question.setAnswerList(answerList);
					singleList.add(question);
				}else if(type == QuestionType.MULT){
					ExamAnswer examAnswer = new ExamAnswer();
					examAnswer.setQuestionId(question.getQuestionId());
					List<ExamAnswer> answerList = examAnswerService.dynamicQuery(examAnswer);
					question.setAnswerList(answerList);
					multList.add(question);
				}else if(type == QuestionType.JUDGE){
					ExamAnswer examAnswer = new ExamAnswer();
					examAnswer.setQuestionId(question.getQuestionId());
					List<ExamAnswer> answerList = examAnswerService.dynamicQuery(examAnswer);
					question.setAnswerList(answerList);
					judgeList.add(question);
				}else if(type == QuestionType.CLOZE){
					clozeList.add(question);
				}else if(type == QuestionType.EXPLAIN){
					explainList.add(question);
				}else if(type == QuestionType.QNQ){
					qnqList.add(question);
				}
			}
			
			map.put("singleList", singleList);
			map.put("multList", multList);
			map.put("judgeList", judgeList);
			map.put("clozeList", clozeList);
			map.put("explainList", explainList);
			map.put("qnqList", qnqList);
			map.put("examPolicy", examPolicy);
			FreeMarketUtil freeMarketUtil = new FreeMarketUtil();
			File file = new File(fileName);
			Writer write = new OutputStreamWriter(new FileOutputStream(file));
			freeMarketUtil.processTemplate("examPaper/paper.ftl", map, write);
		}
	}
	
	/**
	 * 清空集合
	 */
	private void cleanCollection(){
		singleList.clear();
		multList.clear();
		judgeList.clear();
		clozeList.clear();
		explainList.clear();
		qnqList.clear();
	}
	
	/**
	 * 策略和试卷的对应关系
	 */
	class PolicyPaperRef{
		ExamPolicy policy;
		List<Integer> paperIds;
		public ExamPolicy getPolicy() {
			return policy;
		}
		public void setPolicy(ExamPolicy policy) {
			this.policy = policy;
		}
		public List<Integer> getPaperIds() {
			return paperIds;
		}
		public void setPaperIds(List<Integer> paperIds) {
			this.paperIds = paperIds;
		}
	}
	
	/**
	 * 验证字符串是否是数字
	 * @param str	数字的字符串形式
	 * @return
	 */
	private boolean isDigest(String str){
		if(str == null || "".equals(str.trim())){
			return false;
		}
		
		try{
			int sId = Integer.parseInt(str);
			if(sId > 0){
				return true;
			}
		}catch(Exception e){
			log.error("Format for parameter error", e);
		}
		return false;
	}
	/**************************************************************************************/
	public List<PaperQuestion> getSingleList() {
		return singleList;
	}

	public ExamPaper getExamPaper() {
		return examPaper;
	}

	public void setExamPaper(ExamPaper examPaper) {
		this.examPaper = examPaper;
	}

	public ExamPolicy getExamPolicy() {
		return examPolicy;
	}

	public void setExamPolicy(ExamPolicy examPolicy) {
		this.examPolicy = examPolicy;
	}

	public void setSingleList(List<PaperQuestion> singleList) {
		this.singleList = singleList;
	}

	public List<PaperQuestion> getMultList() {
		return multList;
	}

	public void setMultList(List<PaperQuestion> multList) {
		this.multList = multList;
	}

	public List<PaperQuestion> getJudgeList() {
		return judgeList;
	}

	public void setJudgeList(List<PaperQuestion> judgeList) {
		this.judgeList = judgeList;
	}

	public List<PaperQuestion> getClozeList() {
		return clozeList;
	}

	public void setClozeList(List<PaperQuestion> clozeList) {
		this.clozeList = clozeList;
	}

	public List<PaperQuestion> getExplainList() {
		return explainList;
	}

	public void setExplainList(List<PaperQuestion> explainList) {
		this.explainList = explainList;
	}

	public List<PaperQuestion> getQnqList() {
		return qnqList;
	}

	public void setQnqList(List<PaperQuestion> qnqList) {
		this.qnqList = qnqList;
	}

	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getPaperScore() {
		return paperScore;
	}

	public void setPaperScore(Integer paperScore) {
		this.paperScore = paperScore;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getBusinessInfo() {
		return businessInfo;
	}

	public void setBusinessInfo(String businessInfo) {
		this.businessInfo = businessInfo;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
}