package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * @Description: 线路的Dao接口
 * @Author: pengfei.wang
 * @CreateDate: 2020-02-25
 */
public interface RouteDao {

    /**
     *  根据cid查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    public int getTotalCount(int cid, String rname);

    /**
     *  根据cid，start，pageSize查询当前页的数据集合
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    public List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     *  根据id查询线路对象的方法
     * @param rid
     * @return
     */
    public Route findOne(int rid);
}
