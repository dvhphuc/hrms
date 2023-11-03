package com.hrms.global.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {
    private int pageNo;
    private int pageSize;
    private long totalItems;
    private long totalPages;
}

