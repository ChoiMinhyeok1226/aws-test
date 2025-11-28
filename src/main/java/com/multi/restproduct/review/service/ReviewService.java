package com.multi.restproduct.review.service;

import com.multi.restproduct.common.paging.SelectCriteria;
import com.multi.restproduct.review.model.dao.ReviewMapper;
import com.multi.restproduct.review.model.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public List<ReviewDto> findReviewByCode(String id){
        return reviewMapper.findReviewByCode(id);
    }

    public int insertReview(ReviewDto review) {
        return reviewMapper.insertReview(review);
    }

    public List<ReviewDto> selectReviewListWithPaging(SelectCriteria selectCriteria) {
        return reviewMapper.selectReviewListWithPaging(selectCriteria);
    }

    public int selectReviewTotal() {
        return reviewMapper.selectReviewTotal();
    }

    public int updateReview(ReviewDto review) {
        return reviewMapper.updateReview(review);
    }

    public int deleteReview(int code) {
        return reviewMapper.deleteReviewByCode(code);
    }

}
