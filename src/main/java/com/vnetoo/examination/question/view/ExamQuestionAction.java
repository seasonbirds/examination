package com.vnetoo.examination.question.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.view.BaseAction;

import org.springframework.stereotype.Controller;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.app.service.AppCourseService;
import com.vnetoo.examination.enums.QuestionLevel;
import com.vnetoo.examination.enums.QuestionRangeType;
import com.vnetoo.examination.enums.QuestionType;
import com.vnetoo.examination.keypoint.bo.ExamKeyPoint;
import com.vnetoo.examination.keypoint.service.ExamKeyPointService;
import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.bo.ExamQuestion;
import com.vnetoo.examination.question.service.ExamAnswerService;
import com.vnetoo.examination.question.service.ExamQuestionService;

import freemarker.ext.beans.ResourceBundleModel;

/**
 * @comment 题目表Action
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings({ "serial", "restriction" })
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value = "examQuestion", results = {
		@Result(name = "search", type = "freemarker", location = "examQuestion/search.htm"),
		@Result(name = "list", type = "freemarker", location = "examQuestion/list.htm"),
		@Result(name = "edit", type = "freemarker", location = "examQuestion/edit.htm"),
		@Result(name = "show", type = "freemarker", location = "examQuestion/show.htm"),
		@Result(name = "single", type = "freemarker", location = "examQuestion/edit/radio.htm"),
		@Result(name = "mult", type = "freemarker", location = "examQuestion/edit/checkbox.htm"),
		@Result(name = "judge", type = "freemarker", location = "examQuestion/edit/radio.htm"),
		@Result(name = "cloze", type = "freemarker", location = "examQuestion/edit/input.htm"),
		@Result(name = "explain", type = "freemarker", location = "examQuestion/edit/input.htm"),
		@Result(name = "qnq", type = "freemarker", location = "examQuestion/edit/input.htm"),
		@Result(name = "radio", type = "freemarker", location = "examQuestion/show/radio.htm"),
		@Result(name = "checkbox", type = "freemarker", location = "examQuestion/show/checkbox.htm"),
		@Result(name = "input", type = "freemarker", location = "examQuestion/show/input.htm")
		})
public class ExamQuestionAction extends BaseAction<ExamQuestion> {
	@Resource
	private ExamQuestionService examQuestionService;
	@Resource
	private ExamKeyPointService examKeyPointService;
	@Resource
	private ExamAnswerService examAnswerService;
	@Resource
	private AppCourseService appCourseService;
	
	private ExamQuestion examQuestion = new ExamQuestion();

	private String questionFileName;

	private File question;

	private String questionContentType;

	private Integer courseId;

	private Integer keyPointId;

	private QuestionRangeType questionRangeType;
	
	private Integer[] answerIds;
	private String courseName;
	

	private String pointName;
	// EXCEL中题目代码列
	private static final int CODE_NO_COLUMN = 0;
	// EXCEL中题目难度列
	private static final int LEVEL_NO_COLUMN = 1;
	// EXCEL中题目类型列
	private static final int TYPE_NO_COLUMN = 2;
	// EXCEL中答案列
	private static final int ANS_NO_COLUMN = 3;
	// EXCEL中内容列
	private static final int CONTENT_NO_COLUMN = 4;
	// EXCEL中是否正确答案列
	private static final int RIGHT_NO_COLUMN = 5;
	// EXCEL中是否正确备注列
	private static final int MEMO_NO_COLUMN = 6;

	/**************************************************************************************/

	/**
	 * 进入查询题目表页面
	 */
	public String search() {
		query();
		return RESULT_SEARCH;
	}

	/**
	 * 查询题目表
	 */
	public String query() {
		examQuestionService.pageQuery(examQuestion, paginator);
		return RESULT_LIST;
	}

	/**
	 * 进入题目表修改页面 /jsp/examQuestion/CreateExamQuestion.jsp
	 */
	public String toCreateOrUpdate() {
		if (examQuestion.getId() != null
				&& examQuestion.getId().longValue() > 0) {// 修改
			examQuestion = examQuestionService.findById(examQuestion.getId());
			ExamKeyPoint point = examKeyPointService.findById(examQuestion
					.getKeyPointId());
			examQuestion.setCourseId(point.getCourseId());
			pointName=point.getName(); 
			examQuestion.setAnswerList(examAnswerService.getAnswersByQuestionId(examQuestion
					.getId())); 
			switch (examQuestion.getType()) {
			case SINGLE:
				return "single";
			case MULT:
				return "mult";
			case JUDGE:
				return "judge";
			case CLOZE:
				return "cloze";
			case EXPLAIN:
				return "explain";
			case QNQ:
				return "qnq";
			default:
				return null;
			}
		}
		return RESULT_EDIT;
	}
	/**
	 * 显示内容
	 * @return string
	 */
	public String show() {
		if (examQuestion.getId() != null
				&& examQuestion.getId().longValue() > 0) {
			examQuestion = examQuestionService.findById(examQuestion.getId());
			ExamKeyPoint point = examKeyPointService.findById(examQuestion
					.getKeyPointId());
			examQuestion.setCourseId(point.getCourseId());
			courseName=appCourseService.findById(point.getCourseId()).getName();
			pointName=point.getName(); 
			examQuestion.setAnswerList(examAnswerService.getAnswersByQuestionId(examQuestion
					.getId()));
			switch (examQuestion.getType()) {
			case SINGLE:
				return "radio";
			case MULT:
				return "checkbox";
			case JUDGE:
				return "radio";
			case CLOZE:
				return "input";
			case EXPLAIN:
				return "input";
			case QNQ:
				return "input";
			default:
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 * 保存题目表
	 */
	public String save() {
		ExamQuestion po = null;
		if (examQuestion.getId() != null
				&& examQuestion.getId().longValue() > 0) {
			po = examQuestionService.findById(examQuestion.getId());
		}
		try {
			Integer userId = super.getCurrentUser().getId();
			examQuestion.setLastUpdatedBy(userId);
			examQuestion.setMemo("");
			examQuestion.setParse("");
			if (po != null) {
				examQuestion.setCreatedBy(po.getCreatedBy());
				examQuestion.setCode(po.getCode());
				examQuestion.setType(po.getType());
			} else {
				examQuestion.setCreatedBy(userId);
			}
			examQuestionService.saveOrUpdate(examQuestion);

			// 修改答案
			switch (examQuestion.getType()) {
			case SINGLE:
				radio();
				break;
			case MULT:
				checkbox();
				break;
			case JUDGE:
				radio();
				break;
			case CLOZE:
				qnq();
				break;
			case EXPLAIN:
				qnq();
				break;
			case QNQ:
				qnq();
				break;
			default:
				break;
			}
			  
		} catch (Exception e) {
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	private void radio(){
		Integer answerId = Integer.valueOf(ServletActionContext
				.getRequest().getParameter("answerId"));
		List<ExamAnswer> answers = examAnswerService
				.getAnswersByQuestionId(examQuestion.getId());
		for (ExamAnswer item : answers) {
			String answer = ServletActionContext.getRequest().getParameter(
					"answerName" + item.getId());
			item.setContent(answer);
			if (item.getId().equals(answerId)) {
				item.setValid("1");
			} else {
				item.setValid("0");
			}
			examAnswerService.saveOrUpdate(item);
		}
	}
	private void checkbox(){
		List<ExamAnswer> answers = examAnswerService
				.getAnswersByQuestionId(examQuestion.getId());
		List<Integer> list=Arrays.asList(answerIds);
		for (ExamAnswer item : answers) {
			String answer = ServletActionContext.getRequest().getParameter(
					"answerName" + item.getId());
			item.setContent(answer); 
			if(list.contains(item.getId())){
				item.setValid("1");
			}else{
				item.setValid("0");
			} 
			examAnswerService.saveOrUpdate(item);
		}
	}
	private void qnq(){ 
		List<ExamAnswer> answers = examAnswerService
				.getAnswersByQuestionId(examQuestion.getId());
		for (ExamAnswer item : answers) {
			String answer = ServletActionContext.getRequest().getParameter(
					"answerName" + item.getId());
			item.setContent(answer); 
			examAnswerService.saveOrUpdate(item);
		}
	}
	/**
	 * 删除题目表
	 */
	public String remove() {
		try {
			examQuestionService.removeLogic(examQuestion.getId());
		} catch (Exception e) {
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}

	/**
	 * 批量删除题目表
	 */
	public String batchRemove() {
		try {
			for (int i = 0; i < examQuestion.getIds().length; i++) {
				examQuestionService.removeLogic(examQuestion.getIds()[i]);
			}
		} catch (Exception e) {
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}

	/**
	 * 试题导入
	 * 
	 * @return
	 */
	public String upload() {
		// 获取国际化字符串
		ResourceBundleModel rbm = this.getResourceBundle();
		try {
			if (question == null || courseId == null || courseId < 0
					|| questionRangeType == null) {
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			} else {
				//若未设置知识点，将知识点初始化为课程对应的跟知识点
				if(keyPointId == null || keyPointId <=0){
					ExamKeyPoint examKeyPoint = new ExamKeyPoint();
					examKeyPoint.setCourseId(courseId);
					examKeyPoint.setParentId(0);
					List<ExamKeyPoint> keyPointList = examKeyPointService.dynamicQuery(examKeyPoint);
					if(keyPointList == null || keyPointList.isEmpty()){
						throw new Exception("keyPoint initialize incorrectly!");
					}
					keyPointId = keyPointList.get(0).getId();
				}
				List<ExamQuestion> list = new ArrayList<ExamQuestion>();
				Result result = parseExcel(question, list);
				// 解释excel存在错误
				if (result.getCode() != null) {
					String msg = rbm.format(result.getCode(),
							new Object[] { result.errorRowNo + 1 });
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg(msg);
				} else {
					int i = 0;
					for (; i < list.size(); i++) {
						ExamQuestion exam = list.get(i);
						boolean b = examQuestionService.saveExamQuestion(exam);
						if (!b) {
							break;
						}
					}
					// 插入数据库过程中错误
					if (i < list.size()) {
						setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						setResponseMsg(rbm.format("QUESTION.INSERT.ERROR", new Object[] { list.get(i).getCode() }));
					} else {
						setResponseFlag(ExamConstant.RESPONSE_SUCCESS);
						setResponseMsg(rbm.format("QUESTION.IMPORT.SUCCESS", null));
					}
				}
			}
		} catch (Exception e) {
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}

		writeResponse();
		return null;
	}

	/**
	 * Excel文件解释
	 * 
	 * @param file
	 *            excel文件
	 * @param list
	 *            对象列表（size = 0）
	 * @return
	 */
	private Result parseExcel(File file, List<ExamQuestion> list) {
		Result result = new Result();
		Workbook wb = null;
		// 文件格式检查
		try {
			String suffix = questionFileName.substring(questionFileName
					.lastIndexOf(".") + 1);
			if ("xls".equals(suffix)) {
				wb = new HSSFWorkbook(new FileInputStream(question));
			} else if ("xlsx".equals(suffix)) {
				wb = new XSSFWorkbook(new FileInputStream(question));
			} else {
				result = new Result("FILE.FORMAT.NOT.SUPPORT", 0, 0);
			}
		} catch (FileNotFoundException e) {
			result = new Result("FILE.NOT.EXIST", 0, 0);
		} catch (IOException e) {
			result = new Result("FILE.FORMAT.NOT.SUPPORT", 0, 0);
		}

		if (result.getCode() != null) {
			return result;
		}

		// 文件可用内容长度检查
		Sheet sheet = wb.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		if (rowNum < 3) {
			result = new Result("FILE.CONTENT.EMPTY", 0, 0);
			return result;
		}

		ExamQuestion question = null;
		// int userId = super.getCurrentUser().getId();
		int userId = 4;
		int number = 0;
		// 答案索引
		char answerIndex = 'A';
		HashMap<String, String> qCodeMap = new HashMap<String, String>();
		for (int i = 2; i < rowNum + 1; i++) {
			Row row = sheet.getRow(i);
			if (parseLineIsNull(row)) {
				continue;
			}

			String qCode = (row.getCell(CODE_NO_COLUMN) == null) ? "" : row
					.getCell(CODE_NO_COLUMN).getStringCellValue().trim();
			String qLevel = (row.getCell(LEVEL_NO_COLUMN) == null) ? "" : row
					.getCell(LEVEL_NO_COLUMN).getStringCellValue().trim();
			String qType = (row.getCell(TYPE_NO_COLUMN) == null) ? "" : row
					.getCell(TYPE_NO_COLUMN).getStringCellValue().trim();
			String qAnswer = (row.getCell(ANS_NO_COLUMN) == null) ? "" : row
					.getCell(ANS_NO_COLUMN).getStringCellValue().trim();
			String qContent = (row.getCell(CONTENT_NO_COLUMN) == null) ? ""
					: row.getCell(CONTENT_NO_COLUMN).getStringCellValue()
							.trim();
			String qRight = (row.getCell(RIGHT_NO_COLUMN) == null) ? "" : row
					.getCell(RIGHT_NO_COLUMN).getStringCellValue().trim();
			String qMemo = (row.getCell(MEMO_NO_COLUMN) == null) ? "" : row
					.getCell(MEMO_NO_COLUMN).getStringCellValue().trim();

			if (qCode != null && !"".equals(qCode)) {
				// 将上一条题目放进集合中
				if (question != null) {
					if (checkAnswerAndRight(question)) {
						list.add(question);
					} else {
						return new Result("QUESTION.ANSWER.TYPE.NOTMATCH", i, 0);
					}
					number = 0;
				}
				// 判断当前题目的code是否重复
				if (qCodeMap.containsKey(qCode)) {
					return new Result("QUESTION.CODE.REPEAT", i, 0);
				} else {
					qCodeMap.put(qCode, "");
				}
				// 判断当前题目的code是否已经存在数据库
				if (!examQuestionService.checkCode(qCode)) {
					return new Result("QUESTION.CODE.EXIST", i, 0);
				}

				question = new ExamQuestion();
				// 题目难度输入值检查
				if (checkQuestionLevel(qLevel)) {
					question.setLev(QuestionLevel.getLevel(qLevel));
				} else {
					return new Result("QUESTION.LEVEL.INCORRECT", i,
							LEVEL_NO_COLUMN);
				}
				// 题目类型输入值检查
				QuestionType type = null;
				if (checkQuestionType(qType)) {
					type = QuestionType.getQuestionType(qType);
					question.setType(type);
				} else {
					return new Result("QUESTION.TYPE.INCORRECT", i,
							TYPE_NO_COLUMN);
				}
				// 题目内容检查
				if (qContent != null && !"".equals(qContent)) {
					question.setContent(qContent);
				} else {
					return new Result("QUESTION.CONTENT.EMPTY", i,
							CONTENT_NO_COLUMN);
				}

				List<ExamAnswer> l = new ArrayList<ExamAnswer>();
				// 对填空题、名词解释和问答题的处理
				if (type == QuestionType.CLOZE || type == QuestionType.EXPLAIN
						|| type == QuestionType.QNQ) {
					ExamAnswer answer = new ExamAnswer();
					if (qAnswer != null && !"".equals(qAnswer)) {
						answer.setContent(qAnswer);
					} else {
						return new Result("QUESTION.ANSWER.EMPTY", i,
								ANS_NO_COLUMN);
					}
					answer.setValid("1");
					answer.setCreatedBy(userId);
					l.add(answer);
				}
				question.setRangeType(questionRangeType);
				question.setKeyPointId(keyPointId);
				question.setCreatedBy(userId);
				question.setCode(qCode);
				question.setMemo(qMemo);
				question.setAnswerList(l);
			} else {
				// 第一行答案在问题前面
				if (question == null) {
					return new Result("QUESTION.ANSWER.POSITION.ERROR", i, 0);
				}
				// 填空题、名词解释和问答题只占一行
				/*
				 * QuestionType type = QuestionType.getQuestionType(qType);
				 * if(type == QuestionType.CLOZE || type == QuestionType.EXPLAIN
				 * || type == QuestionType.QNQ){ return new
				 * Result("QUESTION.SUBJECTIVE.ERROR", i, 0); }
				 */
				// 答案内容检查
				ExamAnswer answer = new ExamAnswer();
				if (qContent != null && !"".equals(qContent)) {
					answer.setContent(qContent);
				} else {
					return new Result("QUESTION.ANSWER.EMPTY", i,
							CONTENT_NO_COLUMN);
				}
				// 正确答案输入值检查
				if (checkQuestionRight(qRight)) {
					answer.setValid("是".equals(qRight) ? "1" : "0");
				} else {
					return new Result("QUESTION.RIGHT.INCORRECT", i,
							RIGHT_NO_COLUMN);
				}
				answer.setCreatedBy(userId);
				answer.setAnswerIdx(String
						.valueOf((char) (answerIndex + number)));
				// 试题答案列表非空检查
				if (question.getAnswerList() == null) {
					return new Result("QUESTION.ANSWER.LIST.EMPTY", i, 0);
				}
				question.getAnswerList().add(answer);
				number++;
			}
		}
		// 将最后一条题目放进集合中
		if (question != null) {
			// 试题类型和正确答案个数一致性检查
			if (checkAnswerAndRight(question)) {
				list.add(question);
			} else {
				result = new Result("QUESTION.ANSWER.TYPE.NOTMATCH", rowNum, 0);
			}
		}
		// 无可用内容
		if (list.size() == 0) {
			result = new Result("FILE.CONTENT.EMPTY", 0, 0);
		}

		return result;
	}

	/**
	 * 判断是不是空行
	 * 
	 * @return
	 */
	public boolean parseLineIsNull(Row row) {
		if (row != null) {
			for (int i = 0; i < row.getLastCellNum(); i++) {
				Cell cell = row.getCell(i);
				if (cell != null) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
						if (!cell.getStringCellValue().trim().equals("")) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * 判断用户输入的问题难度是否正确
	 */
	private boolean checkQuestionLevel(String level) {
		if (level != null && !"".equals(level)) {
			if ("简单".equals(level.trim()) || "中等".equals(level.trim())
					|| "难".equals(level.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断用户输入的问题类型是否正确
	 */
	private boolean checkQuestionType(String type) {
		if (type != null && !"".equals(type)) {
			if ("单选题".equals(type) || "多选题".equals(type) || "判断题".equals(type)
					|| "填空题".equals(type) || "名词解释".equals(type)
					|| "问答题".equals(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查正确答案的输入值
	 */
	private boolean checkQuestionRight(String right) {
		if (right != null) {
			if ("是".equals(right) || "".equals(right)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 检查试题类型和正确答案个数是否一致
	 * 
	 * @param question
	 * @return
	 */
	private boolean checkAnswerAndRight(ExamQuestion question) {
		List<ExamAnswer> l = question.getAnswerList();
		// 无答案
		if (l == null || l.size() <= 0) {
			return false;
		}

		// 填空题、名词解释和问答题的答案个数大于1
		QuestionType type = question.getType();
		if (type == QuestionType.CLOZE || type == QuestionType.EXPLAIN
				|| type == QuestionType.QNQ) {
			if (l.size() > 1) {
				return false;
			}
		}
		// 判断题的答案个数不等于2
		if (type == QuestionType.JUDGE) {
			if (l.size() != 2) {
				return false;
			}
		}
		// 单选题和多选题的答案个数小于2
		if (type == QuestionType.SINGLE || type == QuestionType.MULT) {
			if (l.size() < 2) {
				return false;
			}
		}
		// 计算正确答案个数
		int rightAnswer = 0;
		for (int i = 0; i < l.size(); i++) {
			ExamAnswer answer = l.get(i);
			if ("1".equals(answer.getValid())) {
				rightAnswer++;
			}
		}
		// 无正确答案
		if (rightAnswer < 1) {
			return false;
		}
		// 除多选题外，其余题型的正确答案个数大于1
		if (type == QuestionType.CLOZE || type == QuestionType.EXPLAIN
				|| type == QuestionType.QNQ || type == QuestionType.SINGLE
				|| type == QuestionType.JUDGE) {
			if (rightAnswer > 1) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 文件解释结果内部类
	 */
	class Result {
		private String code;

		private int errorRowNo;

		private int errorCellNo;

		public Result() {

		}

		public Result(String code, int errorRowNo, int errorCellNo) {
			this.code = code;
			this.errorRowNo = errorRowNo;
			this.errorCellNo = errorCellNo;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public int getErrorRowNo() {
			return errorRowNo;
		}

		public void setErrorRowNo(int errorRowNo) {
			this.errorRowNo = errorRowNo;
		}

		public int getErrorCellNo() {
			return errorCellNo;
		}

		public void setErrorCellNo(int errorCellNo) {
			this.errorCellNo = errorCellNo;
		}
	}

	/**************************************************************************************/

	public void setExamQuestionService(ExamQuestionService examQuestionService) {
		this.examQuestionService = examQuestionService;
	}

	public ExamQuestionService getExamQuestionService() {
		return examQuestionService;
	}

	public void setExamQuestion(ExamQuestion examQuestion) {
		this.examQuestion = examQuestion;
	}

	public ExamQuestion getExamQuestion() {
		return examQuestion;
	}

	public String getQuestionFileName() {
		return questionFileName;
	}

	public void setQuestionFileName(String questionFileName) {
		this.questionFileName = questionFileName;
	}

	public File getQuestion() {
		return question;
	}

	public void setQuestion(File question) {
		this.question = question;
	}

	public String getQuestionContentType() {
		return questionContentType;
	}

	public void setQuestionContentType(String questionContentType) {
		this.questionContentType = questionContentType;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getKeyPointId() {
		return keyPointId;
	}

	public void setKeyPointId(Integer keyPointId) {
		this.keyPointId = keyPointId;
	}

	public QuestionRangeType getQuestionRangeType() {
		return questionRangeType;
	}

	public void setQuestionRangeType(QuestionRangeType questionRangeType) {
		this.questionRangeType = questionRangeType;
	}

	public Integer[] getAnswerIds() {
		return answerIds;
	}

	public void setAnswerIds(Integer[] answerIds) {
		this.answerIds = answerIds;
	}
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
}
