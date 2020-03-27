package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

import java.util.List;

/**
 * @Description:  卖家的Dao
 * @Author: pengfei.wang
 * @CreateDate: 2020-03-02
 */
public interface SellerDao {

    /**
     *  根据sid查询对应的卖家方法
     * @param sid
     * @return
     */
    public Seller findById(int sid);
}
