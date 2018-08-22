package com.gita.backend.dto.common;

/**
 * @author yihang.lv 2018/8/22、16:33
 */
public class PageEnter extends GeneralEnter{
    /**
     * 当前页码
     */
    private int pageNo = 1;
    /**
     * 开始页
     */
    private int startRow = 0;
    /**
     * 每页数量
     */
    private int pageSize = 20;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        this.startRow = this.pageNo-1 == 0 ? 0 : (this.pageNo-1)*this.pageSize;
        return startRow;
    }
}
