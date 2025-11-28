package com.multi.restproduct.review.model.dao;

import com.multi.restproduct.common.paging.SelectCriteria;
import com.multi.restproduct.review.model.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {
    List<ReviewDto> findReviewByCode(String id);


    int insertReview(ReviewDto review);

    List<ReviewDto> selectReviewListWithPaging(SelectCriteria selectCriteria);

    int selectReviewTotal();

    int updateReview(ReviewDto review);

    int deleteReviewByCode(int code);
}
