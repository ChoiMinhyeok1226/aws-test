package com.multi.restproduct.product.controller;

import com.multi.restproduct.common.ResponseDto;
import com.multi.restproduct.common.paging.Pagenation;
import com.multi.restproduct.common.paging.ResponseDtoWithPaging;
import com.multi.restproduct.common.paging.SelectCriteria;
import com.multi.restproduct.product.model.dto.req.RequestProductDto;
import com.multi.restproduct.product.model.dto.res.ProductAllDto;
import com.multi.restproduct.product.service.ProductService;
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
public class ProductController {

    private final ProductService productService;

    @GetMapping("/product/{productid}")
    public ResponseEntity<ResponseDto> findProductbyId(@PathVariable("productid") String productId) {
        ProductAllDto product = productService.findProductbyId(productId);
        if (product == null) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "일치하는 상품 없음", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "상품 조회 성공", product));
    }

    @GetMapping("/product-management/{productid}")
    public ResponseEntity<ResponseDto> findProductbyIdForAdmin(@PathVariable("productid") String productId) {
        ProductAllDto product = productService.findProductbyIdForAdmin(productId);
        if (product == null) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "일치하는 상품 없음", null));
        }
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "상품 조회 성공", product));
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseDto> selectProductListWithPaging(@RequestParam(name = "offset", defaultValue = "1") String offset) {

        //예외 테스트할때
        //  throw new IllegalStateException("Rest 컨트롤러에서 발생한 예외입니다.");


        int totalCount = productService.selectProductTotal();

        SelectCriteria selectCriteria = getSelectCriteria(Integer.parseInt(offset), totalCount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(productService.selectProductListWithPaging(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    @GetMapping("/products-management")
    public ResponseEntity<ResponseDto> selectProductListWithPagingForAdmin(@RequestParam(name = "offset", defaultValue = "1") String offset) {


        int totalCount = productService.selectProductTotalForAdmin();

        SelectCriteria selectCriteria = getSelectCriteria(Integer.parseInt(offset), totalCount);

        ResponseDtoWithPaging responseDtoWithPaging = new ResponseDtoWithPaging();
        responseDtoWithPaging.setPageInfo(selectCriteria);
        responseDtoWithPaging.setData(productService.selectProductListWithPagingForAdmin(selectCriteria));

        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.OK, "조회 성공", responseDtoWithPaging));
    }

    private SelectCriteria getSelectCriteria(int offset, int totalCount) {
        int limit = 10;
        int buttonAmount = 10;
        return Pagenation.getSelectCriteria(offset, totalCount, limit, buttonAmount);
    }

    @GetMapping("/products/search")
    public ResponseEntity<ResponseDto> selectSearchProductList(@RequestParam(value = "query", defaultValue = "") String query) {
        List<ProductAllDto> products = productService.selectSearchProductList(query);

        if (products.isEmpty()) {
            return ResponseEntity.ok(new ResponseDto(HttpStatus.NO_CONTENT, "검색 결과 없음", null));
        }

        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "상품 검색 성공", products));
    }

    @PostMapping("/products-management/products")
    public ResponseEntity<ResponseDto> insertProduct(@ModelAttribute RequestProductDto productDto) {
        log.info("[ProductController] Insert Product: {}", productDto);
        return ResponseEntity.ok().body(new ResponseDto(HttpStatus.CREATED, "상품 입력 성공", productService.insertProduct(productDto)));
    }

    @PutMapping("/products-management/products")
    public ResponseEntity<ResponseDto> updateProduct(@ModelAttribute RequestProductDto productDto) {
        // log.info("[ProductController] Update Product: {}", productDto);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK, "상품 업데이트 성공", productService.updateProduct(productDto)));
    }

}
