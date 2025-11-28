package com.multi.restproduct.product.model.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAllDto {
    private Long productCode;          // 상품 ID (primary key)
    private String productName;        // 상품명
    private int productPrice;          // 상품 가격
    private String productDescription; // 상품 설명
    private String productOrderable;   // 구매 가능 여부 (status)
    private String productImageUrl;    // 상품 이미지 URL
    private String companyId;            // 회사 ID (외래 키)
    private String companyName;        // 회사명 (조인된 정보)
}
