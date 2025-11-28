package com.multi.restproduct.common.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseDtoWithPaging {

    private Object data;
    private SelectCriteria pageInfo;

}
