package com.hrms.global.paging;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingInfo {
    private Integer pageNo;
    private Integer pageSize;
    @Nullable
    private String sortBy;

}
