package com.vnetoo.examination.examReview.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examReview.bo.ExamReviewInfo;
import com.vnetoo.examination.examReview.dao.ExamReviewInfoDAO;

@Service 
public class ExamReviewInfoServiceImpl implements ExamReviewInfoService {
	
	@Resource
	private ExamReviewInfoDAO examReviewInfoDAO;
	
	public List<ExamReviewInfo> selectPage(ExamReviewInfo eri,
			Paginator<ExamReviewInfo> page) {
		return examReviewInfoDAO.selectPage(eri, page);
	}
}
