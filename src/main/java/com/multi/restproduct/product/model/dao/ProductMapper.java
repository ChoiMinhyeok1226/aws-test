package com.multi.restproduct.product.model.dao;


import com.multi.restproduct.common.paging.SelectCriteria;
import com.multi.restproduct.product.model.dto.req.RequestProductDto;
import com.multi.restproduct.product.model.dto.res.ProductAllDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    int selectProductTotal();

    List<ProductAllDto> selectProductListWithPaging(SelectCriteria selectCriteria);

    int insertProduct(RequestProductDto resProductDto);

    ProductAllDto findProductbyId(String s);

    int updateProduct(RequestProductDto resProductDto);

    int selectProductTotalForAdmin();

    List<ProductAllDto> selectProductListWithPagingForAdmin(SelectCriteria selectCriteria);

    ProductAllDto findProductbyIdForAdmin(String productCode);

    List<ProductAllDto> selectSearchProductList(String search);
}
