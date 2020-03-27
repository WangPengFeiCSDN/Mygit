package cn.itcast.travel.domain;

import java.util.List;

/**
 * @Description: 分页对象
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-07
 */
public class PageBean<T> {

     private int totalCount; //总记录数(数据库查询)
     private int totalPage; //总页数 (可以计算出来)
     private int currentPage; //当前页码(用户传参获取)
     private int pageSize; //每页显示的条数(用户传参获取)
     private List<T> list; //每页显示的数据集合 (通过数据库查询)

    //生成Getter和Setter方法

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    //生成toString方法

    @Override
    public String toString() {
        return "PageBean{" +
                "totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
