package com.chen.LeoBlog.base;


public class BaseQuery {
    //第几页
    private Integer page = 1;
    //每页显示的数据量
    private Integer limit = 3;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
