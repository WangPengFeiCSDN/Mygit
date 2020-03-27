package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

/**
 * @Description: 线路图片的Dao
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-02
 */
public interface RouteImgDao {

    /**
     * 根据route的id查询图片的方法
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
