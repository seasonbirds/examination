package com.vnetoo.examination.examReview.dao;

import java.util.List;

import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examReview.bo.ExamReviewInfo;

public interface ExamReviewInfoDAO {

	public List<ExamReviewInfo> selectPage(ExamReviewInfo eri, Paginator<ExamReviewInfo> page);
}
