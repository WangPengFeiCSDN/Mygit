package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

/**
 * @Description: 线路的Service接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-25
 */
public interface RouteService {

    /**
     *  根据类别进行分页查询的方法
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     *  根据id查询线路对象的方法
     * @param rid
     * @return
     */
    public Route findOne(String rid);
}
