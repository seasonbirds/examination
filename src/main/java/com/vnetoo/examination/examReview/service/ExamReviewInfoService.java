package com.vnetoo.examination.examReview.service;

import java.util.List;

import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examReview.bo.ExamReviewInfo;

public interface ExamReviewInfoService {

	public List<ExamReviewInfo> selectPage(ExamReviewInfo eri, Paginator<ExamReviewInfo> page);
}
