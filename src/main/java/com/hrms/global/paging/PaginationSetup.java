package com.hrms.global.paging;

public class PaginationSetup {
    public static <T> Pagination setupPaging(long totalCount, Integer pageNo, Integer pageSize) {
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        return new Pagination(pageNo, pageSize, totalCount, numberOfPages);
    }
}
