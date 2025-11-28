package com.multi.restproduct.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {
    private int reviewCode;
    private int productCode;
    private String productName;
    private String memberCode;
    private String memberName;
    private String reviewTitle;
    private String reviewContent;
    private Date createdDate;
    private Date createdPerson;
    private Date modifiedDate;
    private Date modifiedPerson;
}
