package com.multi.restproduct.review.controller;

import com.multi.restproduct.common.ResponseDto;
import com.multi.restproduct.common.paging.Pagenation;
import com.multi.restproduct.common.paging.ResponseDtoWithPaging;
import com.multi.restproduct.common.paging.SelectCriteria;
import com.multi.restproduct.review.model.dto.ReviewDto;
import com.multi.restproduct.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/review/{reviewcode}")
    public ResponseEntity<ResponseDto> findReviewByCode(@PathVariable("reviewcode") String code) {
        List<ReviewDto> review = reviewService.findReviewByCode(code);
        if (review.isEmpty()) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "데이터가 존재하지 않습니다.", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "조회 성공", review));
    }

    @PostMapping("/review")
    public ResponseEntity<ResponseDto> insertReview(@ModelAttribute ReviewDto review) {
        int result = reviewService.insertReview(review);

        if (result < 1) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.BAD_REQUEST, "리뷰 삽입 실패", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.CREATED, "리뷰 삽입 성공", result));
    }

    @GetMapping("/reviews")
    public ResponseEntity<ResponseDto> selectReviewListWithPaging(@RequestParam("offset") String offset) {

        int totalCount = reviewService.selectReviewTotal();
        SelectCriteria selectCriteria = getSelectCriteria(Integer.parseInt(offset), totalCount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(reviewService.selectReviewListWithPaging(selectCriteria));

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    @PutMapping("/review")
    public ResponseEntity<ResponseDto> updateReview(@ModelAttribute ReviewDto review) {
        log.info("review : {}", review);
        int result = reviewService.updateReview(review);
        if (result < 1) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.BAD_REQUEST, "리뷰 수정 실패", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "리뷰 수정 성공", reviewService.findReviewByCode(String.valueOf(review.getReviewCode()))));
    }

    @DeleteMapping("/review")
    public ResponseEntity<ResponseDto> deleteReview(@RequestParam("reviewcode") int code) {
        int result = reviewService.deleteReview(code);
        if (result < 1) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.BAD_REQUEST, "리뷰 삭제 실패", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "리뷰 삭제 성공", "리뷰 삭제 성공"));
    }

    private SelectCriteria getSelectCriteria(int offset, int totalCount) {

        int limit = 10;
        int buttonAmount = 10;
        return Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
    }
}
